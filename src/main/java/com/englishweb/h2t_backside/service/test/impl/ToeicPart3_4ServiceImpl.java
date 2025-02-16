package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.ToeicPart3_4DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicPart3_4Mapper;
import com.englishweb.h2t_backside.model.test.ToeicPart3_4;
import com.englishweb.h2t_backside.repository.test.ToeicPart3_4Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicPart3_4Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ToeicPart3_4ServiceImpl extends BaseServiceImpl<ToeicPart3_4DTO, ToeicPart3_4, ToeicPart3_4Repository> implements ToeicPart3_4Service {
    private final ToeicPart3_4Mapper mapper;

    public ToeicPart3_4ServiceImpl(ToeicPart3_4Repository repository, DiscordNotifier discordNotifier, ToeicPart3_4Mapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("ToeicPart3_4 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(ToeicPart3_4DTO dto, Exception ex) {
        log.error("Error creating ToeicPart3_4: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating ToeicPart3_4: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(ToeicPart3_4DTO dto, Long id, Exception ex) {
        log.error("Error updating ToeicPart3_4: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating ToeicPart3_4: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("ToeicPart3_4 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(ToeicPart3_4DTO dto, ToeicPart3_4 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ToeicPart3_4 convertToEntity(ToeicPart3_4DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicPart3_4DTO convertToDTO(ToeicPart3_4 entity) {
        return mapper.convertToDTO(entity);
    }
}
