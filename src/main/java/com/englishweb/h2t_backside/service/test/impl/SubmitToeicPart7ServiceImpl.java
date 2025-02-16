package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart7DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitToeicPart7Mapper;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart7;
import com.englishweb.h2t_backside.repository.test.SubmitToeicPart7Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart7Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubmitToeicPart7ServiceImpl extends BaseServiceImpl<SubmitToeicPart7DTO, SubmitToeicPart7, SubmitToeicPart7Repository> implements SubmitToeicPart7Service {
    private final SubmitToeicPart7Mapper mapper;

    public SubmitToeicPart7ServiceImpl(SubmitToeicPart7Repository repository, DiscordNotifier discordNotifier, SubmitToeicPart7Mapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitToeicPart7 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(SubmitToeicPart7DTO dto, Exception ex) {
        log.error("Error creating submit toeic part 7: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit toeic part 7: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(SubmitToeicPart7DTO dto, Long id, Exception ex) {
        log.error("Error updating submit toeic part 7: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit toeic part 7: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitToeicPart7 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(SubmitToeicPart7DTO dto, SubmitToeicPart7 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitToeicPart7 convertToEntity(SubmitToeicPart7DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitToeicPart7DTO convertToDTO(SubmitToeicPart7 entity) {
        return mapper.convertToDTO(entity);
    }
}
