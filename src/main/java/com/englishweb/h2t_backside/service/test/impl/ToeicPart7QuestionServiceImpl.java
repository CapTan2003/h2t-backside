package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.ToeicPart6DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart7DTO;
import com.englishweb.h2t_backside.dto.test.ToeicPart7QuestionDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicPart7QuestionMapper;
import com.englishweb.h2t_backside.model.test.ToeicPart7Question;
import com.englishweb.h2t_backside.repository.test.ToeicPart7QuestionRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicPart7QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ToeicPart7QuestionServiceImpl extends BaseServiceImpl<ToeicPart7QuestionDTO, ToeicPart7Question, ToeicPart7QuestionRepository> implements ToeicPart7QuestionService {
    private final ToeicPart7QuestionMapper mapper;

    public ToeicPart7QuestionServiceImpl(ToeicPart7QuestionRepository repository, DiscordNotifier discordNotifier, ToeicPart7QuestionMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("ToeicPart7Question with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(ToeicPart7QuestionDTO dto, Exception ex) {
        log.error("Error creating ToeicPart7Question: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating ToeicPart7Question: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(ToeicPart7QuestionDTO dto, Long id, Exception ex) {
        log.error("Error updating ToeicPart7Question: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating ToeicPart7Question: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("ToeicPart7Question with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(ToeicPart7QuestionDTO dto, ToeicPart7Question entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ToeicPart7Question convertToEntity(ToeicPart7QuestionDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicPart7QuestionDTO convertToDTO(ToeicPart7Question entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<ToeicPart7QuestionDTO> findByIds(List<Long> ids) {
        List<ToeicPart7QuestionDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
}
