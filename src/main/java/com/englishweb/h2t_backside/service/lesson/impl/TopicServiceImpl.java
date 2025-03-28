package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.ReadingDTO;
import com.englishweb.h2t_backside.dto.lesson.TopicDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.TopicMapper;
import com.englishweb.h2t_backside.model.lesson.Topic;
import com.englishweb.h2t_backside.repository.lesson.TopicRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;
import com.englishweb.h2t_backside.service.lesson.TopicService;
import com.englishweb.h2t_backside.utils.LessonPagination;
import com.englishweb.h2t_backside.utils.ParseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TopicServiceImpl extends BaseServiceImpl<TopicDTO, Topic, TopicRepository> implements TopicService {
    private final TopicMapper mapper;
    private final LessonQuestionService lessonQuestionService;

    public TopicServiceImpl(TopicRepository repository, DiscordNotifier discordNotifier, @Lazy TopicMapper mapper, LessonQuestionService lessonQuestionService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.lessonQuestionService = lessonQuestionService;
    }

    @Override
    public boolean delete(Long id) {
        // Delete other resources associated with the grammar
        TopicDTO dto = super.findById(id);
        lessonQuestionService.deleteAll(dto.getQuestions());
        return super.delete(id);
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Topic with ID '%d' not found.", id);
        log.warn(errorMessage);

        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(TopicDTO dto, Exception ex) {
        log.error("Error creating topic: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating topic: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;

        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(TopicDTO dto, Long id, Exception ex) {
        log.error("Error updating topic: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating topic: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)){
            errorMessage = String.format("Topic with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(TopicDTO dto, Topic entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Topic convertToEntity(TopicDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TopicDTO convertToDTO(Topic entity) {
        return mapper.convertToDTO(entity);
    }


    @Override
    public Page<TopicDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Topic.class
        ).map(this::convertToDTO);
    }

    @Override
    public List<LessonQuestionDTO> findQuestionByLessonId(Long lessonId) {
        try {
            List<Long> listQuestion = findById(lessonId).getQuestions();
            return lessonQuestionService.findByIds(listQuestion);
        } catch (ResourceNotFoundException ex) {
            String errorMessage = String.format("Error finding questions for topic with ID '%d': %s", lessonId, ex.getMessage());
            throw new ResourceNotFoundException(ex.getResourceId(), errorMessage);
        }
    }
}
