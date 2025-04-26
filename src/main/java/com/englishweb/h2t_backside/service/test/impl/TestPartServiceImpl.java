package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.dto.test.TestListeningDTO;
import com.englishweb.h2t_backside.dto.test.TestPartDTO;
import com.englishweb.h2t_backside.dto.test.TestReadingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.TestPartMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.Test;
import com.englishweb.h2t_backside.model.test.TestPart;
import com.englishweb.h2t_backside.repository.test.TestPartRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.*;
import com.englishweb.h2t_backside.utils.QuestionFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TestPartServiceImpl extends BaseServiceImpl<TestPartDTO, TestPart, TestPartRepository> implements TestPartService {

    private final TestReadingService testReadingService;
    private final TestListeningService testListeningService;
    private final TestSpeakingService testSpeakingService;
    private final TestPartMapper mapper;
    private final QuestionService questionService;
    private static final String RESOURCE_NAME = "TestPart";

    public TestPartServiceImpl(TestPartRepository repository,
                               DiscordNotifier discordNotifier,
                               TestPartMapper mapper,
                               TestReadingService testReadingService,
                               TestListeningService testListeningService,
                               TestSpeakingService testSpeakingService, QuestionService questionService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.testReadingService = testReadingService;
        this.testListeningService = testListeningService;
        this.testSpeakingService = testSpeakingService;
        this.questionService = questionService;
    }


    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("TestPart with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(TestPartDTO dto, Exception ex) {
        log.error("Error creating test part: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating test part: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
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

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
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
    @Override
    public int countTotalQuestionsOfTest(List<Long> testParts) {
        if (testParts == null || testParts.isEmpty()) return 0;

        return testParts.stream()
                .mapToInt(partId -> {
                    try {
                        TestPartDTO part = this.findById(partId);
                        List<Long> questionIds = part.getQuestions();

                        return switch (part.getType()) {
                            case VOCABULARY, GRAMMAR, WRITING -> (questionIds != null) ? questionIds.size() : 0;
                            case LISTENING, READING, SPEAKING -> countSubTestQuestions(questionIds, part.getType().name().toLowerCase());
                            default -> 0;
                        };

                    } catch (Exception e) {
                        log.warn("Error with TestPart ID {}: {}", partId, e.getMessage());
                        return 0;
                    }
                })
                .sum();
    }

    private int countSubTestQuestions(List<Long> testIds, String type) {
        if (testIds == null || testIds.isEmpty()) return 0;

        return testIds.stream()
                .mapToInt(testId -> {
                    try {
                        List<Long> questionIds = switch (type) {
                            case "reading" -> testReadingService.findById(testId).getQuestions();
                            case "listening" -> testListeningService.findById(testId).getQuestions();
                            case "speaking" -> testSpeakingService.findById(testId).getQuestions();
                            default -> List.of();
                        };
                        return (questionIds != null) ? questionIds.size() : 0;
                    } catch (Exception e) {
                        log.warn("Failed to load sub-test {}: {}", testId, e.getMessage());
                        return 0;
                    }
                })
                .sum();
    }
    @Override
    public List<TestPartDTO> findByIds(List<Long> ids) {
        List<TestPartDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
    @Override
    public List<QuestionDTO> findQuestionByTestId(Long testId, Boolean status) {
        return QuestionFinder.findQuestionsByTestId(
                testId,
                status,
                RESOURCE_NAME,
                questionService,
                TestPartDTO::getQuestions,
                this::findById
        );
    }



}
