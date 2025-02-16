package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.TestSpeakingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.TestSpeakingMapper;
import com.englishweb.h2t_backside.model.test.TestSpeaking;
import com.englishweb.h2t_backside.repository.test.TestSpeakingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.TestSpeakingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestSpeakingServiceImpl extends BaseServiceImpl<TestSpeakingDTO, TestSpeaking, TestSpeakingRepository> implements TestSpeakingService {
    private final TestSpeakingMapper mapper;

    public TestSpeakingServiceImpl(TestSpeakingRepository repository, DiscordNotifier discordNotifier, TestSpeakingMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("TestSpeaking with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(TestSpeakingDTO dto, Exception ex) {
        log.error("Error creating test speaking: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating test speaking: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(TestSpeakingDTO dto, Long id, Exception ex) {
        log.error("Error updating test speaking: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating test speaking: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("TestSpeaking with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(TestSpeakingDTO dto, TestSpeaking entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected TestSpeaking convertToEntity(TestSpeakingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TestSpeakingDTO convertToDTO(TestSpeaking entity) {
        return mapper.convertToDTO(entity);
    }
}
