package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.ToeicPart5DTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.repository.test.ToeicPart5Repository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicPart5Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ToeicPart5ServiceImpl extends BaseServiceImpl<ToeicPart5DTO, ToeicPart5, ToeicPart5Repository> implements ToeicPart5Service {
    private final ToeicPart5Mapper mapper;

    public ToeicPart5ServiceImpl(ToeicPart5Repository repository, DiscordNotifier discordNotifier, ToeicPart5Mapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("ToeicPart5 with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(ToeicPart5DTO dto, Exception ex) {
        log.error("Error creating ToeicPart5: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating ToeicPart5: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ToeicPart5DTO dto, Long id, Exception ex) {
        log.error("Error updating ToeicPart5: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating ToeicPart5: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("ToeicPart5 with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(ToeicPart5DTO dto, ToeicPart5 entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ToeicPart5 convertToEntity(ToeicPart5DTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicPart5DTO convertToDTO(ToeicPart5 entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<ToeicPart5DTO> findByIds(List<Long> ids) {
        List<ToeicPart5DTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
}
