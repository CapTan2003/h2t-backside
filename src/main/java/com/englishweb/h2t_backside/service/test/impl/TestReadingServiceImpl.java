package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.TestPartDTO;
import com.englishweb.h2t_backside.dto.test.TestReadingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.TestReadingMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.TestReading;
import com.englishweb.h2t_backside.repository.test.TestReadingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.TestReadingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TestReadingServiceImpl extends BaseServiceImpl<TestReadingDTO, TestReading, TestReadingRepository> implements TestReadingService {
    private final TestReadingMapper mapper;

    public TestReadingServiceImpl(TestReadingRepository repository, DiscordNotifier discordNotifier, TestReadingMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("TestReading with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(TestReadingDTO dto, Exception ex) {
        log.error("Error creating test reading: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating test reading: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(TestReadingDTO dto, Long id, Exception ex) {
        log.error("Error updating test reading: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating test reading: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("TestReading with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(TestReadingDTO dto, TestReading entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected TestReading convertToEntity(TestReadingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TestReadingDTO convertToDTO(TestReading entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public List<TestReadingDTO> findByIds(List<Long> ids) {
        List<TestReadingDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
}
