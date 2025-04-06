package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.QuestionMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.Question;
import com.englishweb.h2t_backside.repository.test.QuestionRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class QuestionServiceImpl extends BaseServiceImpl<QuestionDTO, Question, QuestionRepository> implements QuestionService {
    private final QuestionMapper mapper;

    public QuestionServiceImpl(QuestionRepository repository, DiscordNotifier discordNotifier, QuestionMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Question with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(QuestionDTO dto, Exception ex) {
        log.error("Error creating question: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating question: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(QuestionDTO dto, Long id, Exception ex) {
        log.error("Error updating question: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating question: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Question with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(QuestionDTO dto, Question entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Question convertToEntity(QuestionDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected QuestionDTO convertToDTO(Question entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<QuestionDTO> findByIds(List<Long> ids) {
        List<QuestionDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }

}
