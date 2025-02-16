package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart3_4DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitToeicPart3_4Mapper;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart3_4;
import com.englishweb.h2t_backside.repository.test.SubmitToeicPart3_4Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart3_4Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubmitToeicPart3_4ServiceImpl extends BaseServiceImpl<SubmitToeicPart3_4DTO, SubmitToeicPart3_4, SubmitToeicPart3_4Repository> implements SubmitToeicPart3_4Service {
    private final SubmitToeicPart3_4Mapper mapper;

    public SubmitToeicPart3_4ServiceImpl(SubmitToeicPart3_4Repository repository, DiscordNotifier discordNotifier, SubmitToeicPart3_4Mapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitToeicPart3_4 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(SubmitToeicPart3_4DTO dto, Exception ex) {
        log.error("Error creating submit toeic part 3_4: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit toeic part 3_4: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(SubmitToeicPart3_4DTO dto, Long id, Exception ex) {
        log.error("Error updating submit toeic part 3_4: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit toeic part 3_4: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitToeicPart3_4 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(SubmitToeicPart3_4DTO dto, SubmitToeicPart3_4 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitToeicPart3_4 convertToEntity(SubmitToeicPart3_4DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitToeicPart3_4DTO convertToDTO(SubmitToeicPart3_4 entity) {
        return mapper.convertToDTO(entity);
    }
}
