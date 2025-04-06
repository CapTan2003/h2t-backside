package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.filter.TestFilterDTO;
import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.TestMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.Test;
import com.englishweb.h2t_backside.repository.test.TestRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.*;
import com.englishweb.h2t_backside.utils.TestPagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestServiceImpl extends BaseServiceImpl<TestDTO, Test, TestRepository> implements TestService {
    private final TestMapper mapper;
    private final SubmitTestService submitTestService;
    private final TestPartService testPartService;

    public TestServiceImpl(TestRepository repository, DiscordNotifier discordNotifier,
                           TestMapper mapper,
                           SubmitTestService submitTestService,
                           TestPartService testPartService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.submitTestService = submitTestService;
        this.testPartService = testPartService;
    }
    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Test with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(TestDTO dto, Exception ex) {
        log.error("Error creating test: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating test: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(TestDTO dto, Long id, Exception ex) {
        log.error("Error updating test: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating test: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Test with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    protected void patchEntityFromDTO(TestDTO dto, Test entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Test convertToEntity(TestDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected TestDTO convertToDTO(Test entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<TestDTO> searchWithFilters(int page, int size, String sortFields, TestFilterDTO filter, String userId) {
        return TestPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Test.class
        ).map(entity -> {
            TestDTO dto = mapper.convertToDTO(entity);
            dto.setTotalQuestions(testPartService.countTotalQuestionsOfTest(dto.getParts()));
            dto.setScoreLastOfTest(submitTestService.getScoreOfLastTestByUser(Long.valueOf(userId)));
            return dto;
        });
    }

}
