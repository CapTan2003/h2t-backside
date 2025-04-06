package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.ListenAndWriteAWordDTO;
import com.englishweb.h2t_backside.dto.lesson.ListeningDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.ListeningMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.Listening;
import com.englishweb.h2t_backside.repository.lesson.ListeningRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;
import com.englishweb.h2t_backside.service.lesson.ListenAndWriteAWordService;
import com.englishweb.h2t_backside.service.lesson.ListeningService;
import com.englishweb.h2t_backside.utils.LessonPagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ListeningServiceImpl extends BaseServiceImpl<ListeningDTO, Listening, ListeningRepository> implements ListeningService {

    private final ListeningMapper mapper;
    private final LessonQuestionService lessonQuestionService;
    private final ListenAndWriteAWordService listenAndWriteAWordService;

    public ListeningServiceImpl(ListeningRepository repository,
                                DiscordNotifierImpl discordNotifier,
                                @Lazy ListeningMapper mapper,
                                LessonQuestionService lessonQuestionService,
                                @Lazy ListenAndWriteAWordService listenAndWriteAWordService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.lessonQuestionService = lessonQuestionService;
        this.listenAndWriteAWordService = listenAndWriteAWordService;
    }

    @Override
    public boolean delete(Long id) {
        // Delete other resources associated with the listening
        ListeningDTO dto = super.findById(id);
        lessonQuestionService.deleteAll(dto.getQuestions());
        List<ListenAndWriteAWordDTO> listDTO = listenAndWriteAWordService.findByListeningId(id);
        listenAndWriteAWordService.deleteAll(listDTO.stream().map(ListenAndWriteAWordDTO::getId).toList());
        return super.delete(id);
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Listening with ID '%d' not found.", id);
        log.warn(errorMessage);

        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(ListeningDTO dto, Exception ex) {
        log.error("Error creating listening: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating listening: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;

        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ListeningDTO dto, Long id, Exception ex) {
        log.error("Error updating listening: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating listening: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)){
            errorMessage = String.format("Listening with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(ListeningDTO dto, Listening entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Listening convertToEntity(ListeningDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ListeningDTO convertToDTO(Listening entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<ListeningDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Listening.class
        ).map(this::convertToDTO);
    }

    @Override
    public List<LessonQuestionDTO> findQuestionByLessonId(Long lessonId) {
        try {
            List<Long> listQuestion = findById(lessonId).getQuestions();
            return lessonQuestionService.findByIds(listQuestion);
        } catch (ResourceNotFoundException ex) {
            String errorMessage = String.format("Error finding questions for listening with ID '%d': %s", lessonId, ex.getMessage());
            throw new ResourceNotFoundException(ex.getResourceId(), errorMessage, SeverityEnum.LOW);
        }
    }
}
