package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.SubmitTestDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitTestMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.repository.test.SubmitTestRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.SubmitTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class SubmitTestServiceImpl extends BaseServiceImpl<SubmitTestDTO, SubmitTest, SubmitTestRepository> implements SubmitTestService {
    private final SubmitTestMapper mapper;

    public SubmitTestServiceImpl(SubmitTestRepository repository, DiscordNotifier discordNotifier, SubmitTestMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitTest with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SubmitTestDTO dto, Exception ex) {
        log.error("Error creating submit test: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit test: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(SubmitTestDTO dto, Long id, Exception ex) {
        log.error("Error updating submit test: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating submit test: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("SubmitTest with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(SubmitTestDTO dto, SubmitTest entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected SubmitTest convertToEntity(SubmitTestDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected SubmitTestDTO convertToDTO(SubmitTest entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public double getScoreOfLastTestByUser(Long userId) {
        List<SubmitTest> submits = repository.findByUserIdAndStatusTrue(userId);

        return Double.valueOf(submits.stream()
                .max(Comparator.comparing(SubmitTest::getCreatedAt))
                .map(SubmitTest::getScore)
                .orElse( 0));
    }
    @Override
    public int countSubmitByUserId(Long userId) {
        return repository.countByUserIdAndStatusTrue(userId);
    }

    @Override
    public double totalScoreByUserId(Long userId) {
        return repository.sumScoreByUserIdAndStatusTrue(userId);
    }
    @Override
    public SubmitTestDTO findByTestIdAndUserIdAndStatusFalse(Long testId, Long userId) {
        SubmitTest submitTest = repository.findByTestIdAndUserIdAndStatusFalse(testId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        testId,
                        String.format("SubmitTest with test ID '%d', userId '%d' and status=false not found.", testId, userId),
                        SeverityEnum.LOW
                ));
        return convertToDTO(submitTest);
    }



}
