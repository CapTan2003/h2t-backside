package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitCompetitionMapper;
import com.englishweb.h2t_backside.model.test.SubmitCompetition;
import com.englishweb.h2t_backside.repository.test.SubmitCompetitionRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitCompetitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubmitCompetitionServiceImpl extends BaseServiceImpl<SubmitCompetitionDTO, SubmitCompetition, SubmitCompetitionRepository> implements SubmitCompetitionService {
    private final SubmitCompetitionMapper mapper;

    public SubmitCompetitionServiceImpl(SubmitCompetitionRepository repository, DiscordNotifier discordNotifier, SubmitCompetitionMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitCompetition with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(SubmitCompetitionDTO dto, Exception ex) {
        log.error("Error creating submit competition: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit competition: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(SubmitCompetitionDTO dto, Long id, Exception ex) {
        log.error("Error updating submit competition: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit competition: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitCompetition with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(SubmitCompetitionDTO dto, SubmitCompetition entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitCompetition convertToEntity(SubmitCompetitionDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitCompetitionDTO convertToDTO(SubmitCompetition entity) {
        return mapper.convertToDTO(entity);
    }
}
