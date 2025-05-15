package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.filter.ToeicFilterDTO;
import com.englishweb.h2t_backside.dto.test.*;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.model.test.Toeic;
import com.englishweb.h2t_backside.model.test.ToeicQuestion;
import com.englishweb.h2t_backside.repository.specifications.CompetitionTestSpecification;
import com.englishweb.h2t_backside.repository.specifications.ToeicSpecification;
import com.englishweb.h2t_backside.repository.test.ToeicRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.*;
import com.englishweb.h2t_backside.utils.BaseFilterSpecification;
import com.englishweb.h2t_backside.utils.ParseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ToeicServiceImpl extends BaseServiceImpl<ToeicDTO, Toeic, ToeicRepository> implements ToeicService {
    private final ToeicMapper mapper;
    private final ToeicPart1Service toeicPart1Service;
    private final ToeicPart2Service toeicPart2Service;
    private final ToeicQuestionService toeicQuestionService;
    private final SubmitToeicService submitToeicService;
    private final ToeicPart3_4Service toeicPart3_4Service;
    private final ToeicPart6Service toeicPart6Service;
    private final ToeicPart7Service toeicPart7Service;
    private static final int PART1_STANDARD_QUESTIONS = 6;
    private static final int PART2_STANDARD_QUESTIONS = 25;
    private static final int PART3_TOTAL_QUESTIONS = 39; // 13 conversations x 3 questions
    private static final int PART4_TOTAL_QUESTIONS = 30; // 10 talks x 3 questions
    private static final int PART5_STANDARD_QUESTIONS = 40;
    private static final int PART6_TOTAL_QUESTIONS = 16; // 4 passages x 4 questions
    private static final int PART7_TOTAL_QUESTIONS = 54;
    private static final int TOTAL_QUESTIONS = 200;


    public ToeicServiceImpl(ToeicRepository repository, DiscordNotifier discordNotifier, ToeicMapper mapper, ToeicPart1Service toeicPart1Service, ToeicPart2Service toeicPart2Service, ToeicQuestionService toeicQuestionService, SubmitToeicService submitToeicService, ToeicPart3_4Service toeicPart34Service, ToeicPart6Service toeicPart6Service, ToeicPart7Service toeicPart7Service) {
        super(repository, discordNotifier);
        this.toeicPart1Service = toeicPart1Service;
        this.toeicPart2Service = toeicPart2Service;
        this.toeicQuestionService = toeicQuestionService;
        this.submitToeicService = submitToeicService;
        this.mapper = mapper;
        this.toeicPart3_4Service = toeicPart34Service;
        this.toeicPart6Service = toeicPart6Service;
        this.toeicPart7Service = toeicPart7Service;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Toeic with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(ToeicDTO dto, Exception ex) {
        log.error("Error creating Toeic: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating Toeic: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ToeicDTO dto, Long id, Exception ex) {
        log.error("Error updating Toeic: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating Toeic: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Toeic with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    public boolean delete(Long id) {
        ToeicDTO dto = super.findById(id);
        if (dto == null) return false;

        List<SubmitToeicDTO> submitToeicDTOS = submitToeicService.findByToeicId(dto.getId());
        if (submitToeicDTOS != null && !submitToeicDTOS.isEmpty()) {
            for (SubmitToeicDTO submitToeicDTO : submitToeicDTOS) {
                submitToeicService.delete(submitToeicDTO.getId());
            }
        }

        if (dto.getQuestionsPart1() != null) {
            for (Long questionPart1 : dto.getQuestionsPart1()) {
                toeicPart1Service.delete(questionPart1);
            }
        }

        if (dto.getQuestionsPart2() != null) {
            for (Long questionPart2 : dto.getQuestionsPart2()) {
                toeicPart2Service.delete(questionPart2);
            }
        }

        if (dto.getQuestionsPart3() != null) {
            for (Long questionPart3 : dto.getQuestionsPart3()) {
                toeicPart3_4Service.delete(questionPart3);
            }
        }

        if (dto.getQuestionsPart4() != null) {
            for (Long questionPart4 : dto.getQuestionsPart4()) {
                toeicPart3_4Service.delete(questionPart4);
            }
        }

        if (dto.getQuestionsPart5() != null) {
            for (Long questionPart5 : dto.getQuestionsPart5()) {
                toeicQuestionService.delete(questionPart5);
            }
        }

        if (dto.getQuestionsPart6() != null) {
            for (Long questionPart6 : dto.getQuestionsPart6()) {
                toeicPart6Service.delete(questionPart6);
            }
        }

        if (dto.getQuestionsPart7() != null) {
            for (Long questionPart7 : dto.getQuestionsPart7()) {
                toeicPart7Service.delete(questionPart7);
            }
        }

        return super.delete(id);
    }

    @Override
    protected void patchEntityFromDTO(ToeicDTO dto, Toeic entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Toeic convertToEntity(ToeicDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicDTO convertToDTO(Toeic entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public Page<ToeicDTO> searchWithFilters(int page, int size, String sortFields, ToeicFilterDTO filter, Long userId, Long ownerId) {
        Pageable pageable = ParseData.parsePageArgs(page, size, sortFields, Toeic.class);

        Specification<Toeic> specification = BaseFilterSpecification.applyBaseFilters(filter);

        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            specification = specification.and(ToeicSpecification.findByName(filter.getTitle()));
        }
        if (ownerId != null) {
            specification = specification.and(ToeicSpecification.findByOwnerId(ownerId));
        }

        return repository.findAll(specification, pageable).map(entity -> {
            ToeicDTO dto = mapper.convertToDTO(entity);
            dto.setScoreLastOfTest(submitToeicService.getScoreOfLastTestByUserAndToeic(userId, dto.getId()));
            return dto;
        });
    }

    @Override
    public boolean verifyValidToeic(Long toeicId) {
        ToeicDTO toeic = super.findById(toeicId);

        if (toeic == null) {
            return false;
        }

        if (toeic.getQuestionsPart1() == null || toeic.getQuestionsPart1().size() != PART1_STANDARD_QUESTIONS) {
            log.warn("Part 1 invalid: {} questions (expected: {})",
                    toeic.getQuestionsPart1() != null ? toeic.getQuestionsPart1().size() : 0,
                    PART1_STANDARD_QUESTIONS);
            return false;
        }


        // Verify Part 2 - Question response (25 questions)
        if (toeic.getQuestionsPart2() == null || toeic.getQuestionsPart2().size() != PART2_STANDARD_QUESTIONS) {
            log.warn("Part 2 invalid: {} questions (expected: {})",
                    toeic.getQuestionsPart2() != null ? toeic.getQuestionsPart2().size() : 0,
                    PART2_STANDARD_QUESTIONS);
            return false;
        }

        // Verify Part 3 - Short conversations (39 questions)
        List<ToeicPart3_4DTO> part3List = toeicPart3_4Service.findByIdsAndStatus(toeic.getQuestionsPart3(),true);
        int part3TotalQuestions = 0;
        for (ToeicPart3_4DTO part3 : part3List) {
            if (part3.getQuestions() != null) {
                part3TotalQuestions += part3.getQuestions().size();
            }
        }
        if (part3TotalQuestions != PART3_TOTAL_QUESTIONS) {
            log.warn("Part 3 invalid: {} questions (expected: {})", part3TotalQuestions, PART3_TOTAL_QUESTIONS);
            return false;
        }

        // Verify Part 4 - Short talks (30 questions)
        List<ToeicPart3_4DTO> part4List = toeicPart3_4Service.findByIdsAndStatus(toeic.getQuestionsPart4(),true);
        int part4TotalQuestions = 0;
        for (ToeicPart3_4DTO part4 : part4List) {
            if (part4.getQuestions() != null) {
                part4TotalQuestions += part4.getQuestions().size();
            }
        }
        if (part4TotalQuestions != PART4_TOTAL_QUESTIONS) {
            log.warn("Part 4 invalid: {} questions (expected: {})", part4TotalQuestions, PART4_TOTAL_QUESTIONS);
            return false;
        }

        // Verify Part 5 - Incomplete sentences (40 questions)
        if (toeic.getQuestionsPart5() == null || toeic.getQuestionsPart5().size() != PART5_STANDARD_QUESTIONS) {
            log.warn("Part 5 invalid: {} questions (expected: {})",
                    toeic.getQuestionsPart5() != null ? toeic.getQuestionsPart5().size() : 0,
                    PART5_STANDARD_QUESTIONS);
            return false;
        }

        // Verify Part 6 - Text completion (16 questions)
        List<ToeicPart6DTO> part6List = toeicPart6Service.findByIdsAndStatus(toeic.getQuestionsPart6(),true);
        int part6TotalQuestions = 0;
        for (ToeicPart6DTO part6 : part6List) {
            if (part6.getQuestions() != null) {
                part6TotalQuestions += part6.getQuestions().size();
            }
        }
        if (part6TotalQuestions != PART6_TOTAL_QUESTIONS) {
            log.warn("Part 6 invalid: {} questions (expected: {})", part6TotalQuestions, PART6_TOTAL_QUESTIONS);
            return false;
        }

        // Verify Part 7 - Reading comprehension (54 questions)
        List<ToeicPart7DTO> part7List = toeicPart7Service.findByIdsAndStatus(toeic.getQuestionsPart7(),true);
        int part7TotalQuestions = 0;
        for (ToeicPart7DTO part7 : part7List) {
            if (part7.getQuestions() != null) {
                part7TotalQuestions += part7.getQuestions().size();
            }
        }
        if (part7TotalQuestions != PART7_TOTAL_QUESTIONS) {
            log.warn("Part 7 invalid: {} questions (expected: {})", part7TotalQuestions, PART7_TOTAL_QUESTIONS);
            return false;
        }

        log.info("TOEIC test verification passed for ID: {}", toeicId);
        return true;
    }
}
