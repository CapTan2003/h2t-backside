package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.PreparationDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.PreparationMapper;
import com.englishweb.h2t_backside.model.lesson.Preparation;
import com.englishweb.h2t_backside.repository.lesson.PreparationRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.PreparationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PreparationServiceImpl extends BaseServiceImpl<PreparationDTO, Preparation, PreparationRepository>
        implements PreparationService {

    private final PreparationMapper mapper;

    public PreparationServiceImpl(PreparationRepository repository,
                                  DiscordNotifier discordNotifier,
                                  PreparationMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Preparation with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(PreparationDTO dto, Exception ex) {
        log.error("Error creating preparation: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating preparation: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.PREPARATION_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(PreparationDTO dto, Long id, Exception ex) {
        log.error("Error updating preparation: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating preparation: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.PREPARATION_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Preparation with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(PreparationDTO dto, Preparation entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Preparation convertToEntity(PreparationDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected PreparationDTO convertToDTO(Preparation entity) {
        return mapper.convertToDTO(entity);
    }
}
