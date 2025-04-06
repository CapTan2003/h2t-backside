package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart6DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitToeicPart6Mapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart6;
import com.englishweb.h2t_backside.repository.test.SubmitToeicPart6Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart6Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubmitToeicPart6ServiceImpl extends BaseServiceImpl<SubmitToeicPart6DTO, SubmitToeicPart6, SubmitToeicPart6Repository> implements SubmitToeicPart6Service {
    private final SubmitToeicPart6Mapper mapper;

    public SubmitToeicPart6ServiceImpl(SubmitToeicPart6Repository repository, DiscordNotifier discordNotifier, SubmitToeicPart6Mapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitToeicPart6 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SubmitToeicPart6DTO dto, Exception ex) {
        log.error("Error creating submit toeic part 6: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit toeic part 6: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitToeicPart6DTO dto, Long id, Exception ex) {
        log.error("Error updating submit toeic part 6: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit toeic part 6: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitToeicPart6 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(SubmitToeicPart6DTO dto, SubmitToeicPart6 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitToeicPart6 convertToEntity(SubmitToeicPart6DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitToeicPart6DTO convertToDTO(SubmitToeicPart6 entity) {
        return mapper.convertToDTO(entity);
    }
}
