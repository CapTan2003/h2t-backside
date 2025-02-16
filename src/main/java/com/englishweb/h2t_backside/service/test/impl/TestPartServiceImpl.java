package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.TestPartDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.TestPartMapper;
import com.englishweb.h2t_backside.model.test.TestPart;
import com.englishweb.h2t_backside.repository.test.TestPartRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.TestPartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestPartServiceImpl extends BaseServiceImpl<TestPartDTO, TestPart, TestPartRepository> implements TestPartService {
    private final TestPartMapper mapper;

    public TestPartServiceImpl(TestPartRepository repository, DiscordNotifier discordNotifier, TestPartMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("TestPart with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(TestPartDTO dto, Exception ex) {
        log.error("Error creating test part: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating test part: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(TestPartDTO dto, Long id, Exception ex) {
        log.error("Error updating test part: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating test part: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("TestPart with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(TestPartDTO dto, TestPart entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected TestPart convertToEntity(TestPartDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TestPartDTO convertToDTO(TestPart entity) {
        return mapper.convertToDTO(entity);
    }
}
