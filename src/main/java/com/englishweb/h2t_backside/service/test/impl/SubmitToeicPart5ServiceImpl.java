package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart5DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitToeicPart5Mapper;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart5;
import com.englishweb.h2t_backside.repository.test.SubmitToeicPart5Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart5Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubmitToeicPart5ServiceImpl extends BaseServiceImpl<SubmitToeicPart5DTO, SubmitToeicPart5, SubmitToeicPart5Repository> implements SubmitToeicPart5Service {
    private final SubmitToeicPart5Mapper mapper;

    public SubmitToeicPart5ServiceImpl(SubmitToeicPart5Repository repository, DiscordNotifier discordNotifier, SubmitToeicPart5Mapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitToeicPart5 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(SubmitToeicPart5DTO dto, Exception ex) {
        log.error("Error creating submit toeic part 5: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit toeic part 5: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(SubmitToeicPart5DTO dto, Long id, Exception ex) {
        log.error("Error updating submit toeic part 5: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit toeic part 5: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitToeicPart5 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(SubmitToeicPart5DTO dto, SubmitToeicPart5 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitToeicPart5 convertToEntity(SubmitToeicPart5DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitToeicPart5DTO convertToDTO(SubmitToeicPart5 entity) {
        return mapper.convertToDTO(entity);
    }
}
