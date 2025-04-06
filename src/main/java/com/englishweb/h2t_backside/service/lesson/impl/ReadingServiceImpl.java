package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.ReadingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.ReadingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.lesson.Reading;
import com.englishweb.h2t_backside.repository.lesson.ReadingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;
import com.englishweb.h2t_backside.service.lesson.ReadingService;
import com.englishweb.h2t_backside.utils.LessonPagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReadingServiceImpl extends BaseServiceImpl<ReadingDTO, Reading, ReadingRepository> implements ReadingService {

    private final ReadingMapper mapper;
    private final LessonQuestionService lessonQuestionService;

    public ReadingServiceImpl(ReadingRepository repository, DiscordNotifier discordNotifier, @Lazy ReadingMapper mapper, LessonQuestionService lessonQuestionService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.lessonQuestionService = lessonQuestionService;
    }

    @Override
    public boolean delete(Long id) {
        // Delete other resources associated with the reading
        ReadingDTO dto = super.findById(id);
        lessonQuestionService.deleteAll(dto.getQuestions());
        return super.delete(id);
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Reading with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(ReadingDTO dto, Exception ex) {
        log.error("Error creating reading: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating reading: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ReadingDTO dto, Long id, Exception ex) {
        log.error("Error updating reading: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating reading: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Reading with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(ReadingDTO dto, Reading entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Reading convertToEntity(ReadingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ReadingDTO convertToDTO(Reading entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<ReadingDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Reading.class
        ).map(this::convertToDTO);
    }

    @Override
    public List<LessonQuestionDTO> findQuestionByLessonId(Long lessonId) {
        try {
            List<Long> listQuestion = findById(lessonId).getQuestions();
            return lessonQuestionService.findByIds(listQuestion);
        } catch (ResourceNotFoundException ex) {
            String errorMessage = String.format("Error finding questions for reading with ID '%d': %s", lessonId, ex.getMessage());
            throw new ResourceNotFoundException(ex.getResourceId(), errorMessage, SeverityEnum.LOW);
        }
    }
}