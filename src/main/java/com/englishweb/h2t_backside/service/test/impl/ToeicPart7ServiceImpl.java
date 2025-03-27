package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.ToeicPart7DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicPart7Mapper;
import com.englishweb.h2t_backside.model.test.ToeicPart7;
import com.englishweb.h2t_backside.repository.test.ToeicPart7Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicPart7Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ToeicPart7ServiceImpl extends BaseServiceImpl<ToeicPart7DTO, ToeicPart7, ToeicPart7Repository> implements ToeicPart7Service {
    private final ToeicPart7Mapper mapper;

    public ToeicPart7ServiceImpl(ToeicPart7Repository repository, DiscordNotifier discordNotifier, ToeicPart7Mapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("ToeicPart7 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(ToeicPart7DTO dto, Exception ex) {
        log.error("Error creating ToeicPart7: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating ToeicPart7: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(ToeicPart7DTO dto, Long id, Exception ex) {
        log.error("Error updating ToeicPart7: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating ToeicPart7: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("ToeicPart7 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(ToeicPart7DTO dto, ToeicPart7 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ToeicPart7 convertToEntity(ToeicPart7DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicPart7DTO convertToDTO(ToeicPart7 entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<ToeicPart7DTO> findByIds(List<Long> ids) {
        List<ToeicPart7DTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
}
