package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.GrammarDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.GrammarMapper;
import com.englishweb.h2t_backside.model.lesson.Grammar;
import com.englishweb.h2t_backside.repository.lesson.GrammarRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.GrammarService;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;
import com.englishweb.h2t_backside.utils.LessonPagination;
import com.englishweb.h2t_backside.utils.ParseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GrammarServiceImpl extends BaseServiceImpl<GrammarDTO, Grammar, GrammarRepository> implements GrammarService {
    private final GrammarMapper mapper;
    private final LessonQuestionService lessonQuestionService;

    public GrammarServiceImpl(GrammarRepository repository, DiscordNotifier discordNotifier, GrammarMapper mapper, LessonQuestionService lessonQuestionService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.lessonQuestionService = lessonQuestionService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Grammar with ID '%d' not found.", id);
        log.warn(errorMessage);

        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(GrammarDTO dto, Exception ex) {
        log.error("Error creating grammar: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating grammar: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;

        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(GrammarDTO dto, Long id, Exception ex) {
        log.error("Error updating grammar: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating grammar: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)){
            errorMessage = String.format("Grammar with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(GrammarDTO dto, Grammar entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Grammar convertToEntity(GrammarDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected GrammarDTO convertToDTO(Grammar entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<GrammarDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Grammar.class
        ).map(this::convertToDTO);
    }

    @Override
    public List<LessonQuestionDTO> findQuestionByLessonId(Long lessonId) {
        try {
            List<Long> listQuestion = ParseData.parseStringToLongList(findById(lessonId).getQuestions());
            return lessonQuestionService.findByIds(listQuestion);
        } catch (ResourceNotFoundException ex) {
            String errorMessage = String.format("Error finding questions for grammar with ID '%d': %s", lessonId, ex.getMessage());
            throw new ResourceNotFoundException(ex.getResourceId(), errorMessage);
        }
    }
}
