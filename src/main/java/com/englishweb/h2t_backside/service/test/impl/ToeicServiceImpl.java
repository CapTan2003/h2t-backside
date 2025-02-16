package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.ToeicDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicMapper;
import com.englishweb.h2t_backside.model.test.Toeic;
import com.englishweb.h2t_backside.repository.test.ToeicRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ToeicServiceImpl extends BaseServiceImpl<ToeicDTO, Toeic, ToeicRepository> implements ToeicService {
    private final ToeicMapper mapper;

    public ToeicServiceImpl(ToeicRepository repository, DiscordNotifier discordNotifier, ToeicMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Toeic with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(ToeicDTO dto, Exception ex) {
        log.error("Error creating Toeic: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating Toeic: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(ToeicDTO dto, Long id, Exception ex) {
        log.error("Error updating Toeic: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating Toeic: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Toeic with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(ToeicDTO dto, Toeic entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Toeic convertToEntity(ToeicDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicDTO convertToDTO(Toeic entity) {
        return mapper.convertToDTO(entity);
    }
}
