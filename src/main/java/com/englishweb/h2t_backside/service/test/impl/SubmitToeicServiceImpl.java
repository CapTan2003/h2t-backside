package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitToeicDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitToeicMapper;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.repository.test.SubmitToeicRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitToeicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class SubmitToeicServiceImpl extends BaseServiceImpl<SubmitToeicDTO, SubmitToeic, SubmitToeicRepository> implements SubmitToeicService {
    private final SubmitToeicMapper mapper;

    public SubmitToeicServiceImpl(SubmitToeicRepository repository, DiscordNotifier discordNotifier, SubmitToeicMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitToeic with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(SubmitToeicDTO dto, Exception ex) {
        log.error("Error creating submit toeic: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit toeic: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(SubmitToeicDTO dto, Long id, Exception ex) {
        log.error("Error updating submit toeic: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit toeic: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitToeic with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(SubmitToeicDTO dto, SubmitToeic entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitToeic convertToEntity(SubmitToeicDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitToeicDTO convertToDTO(SubmitToeic entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public Double getScoreOfLastTestByUser(Long userId) {
        List<SubmitToeic> submits = repository.findByUserIdAndStatusTrue(userId);

        return Double.valueOf(submits.stream()
                .max(Comparator.comparing(SubmitToeic::getCreatedAt))
                .map(SubmitToeic::getScore)
                .orElse(0));
    }
    @Override
    public int countSubmitByUserId(Long userId) {
        return repository.countByUserIdAndStatusTrue(userId);
    }

    @Override
    public double totalScoreByUserId(Long userId) {
        return repository.sumScoreByUserIdAndStatusTrue(userId);
    }
}
