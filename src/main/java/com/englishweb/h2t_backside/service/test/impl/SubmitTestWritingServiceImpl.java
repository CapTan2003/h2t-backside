package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitTestWritingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitTestWritingMapper;
import com.englishweb.h2t_backside.model.test.SubmitTestWriting;
import com.englishweb.h2t_backside.repository.test.SubmitTestWritingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitTestWritingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubmitTestWritingServiceImpl extends BaseServiceImpl<SubmitTestWritingDTO, SubmitTestWriting, SubmitTestWritingRepository> implements SubmitTestWritingService {
    private final SubmitTestWritingMapper mapper;

    public SubmitTestWritingServiceImpl(SubmitTestWritingRepository repository, DiscordNotifier discordNotifier, SubmitTestWritingMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitTestWriting with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(SubmitTestWritingDTO dto, Exception ex) {
        log.error("Error creating submit test writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit test writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(SubmitTestWritingDTO dto, Long id, Exception ex) {
        log.error("Error updating submit test writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit test writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitTestWriting with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(SubmitTestWritingDTO dto, SubmitTestWriting entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitTestWriting convertToEntity(SubmitTestWritingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitTestWritingDTO convertToDTO(SubmitTestWriting entity) {
        return mapper.convertToDTO(entity);
    }
}
