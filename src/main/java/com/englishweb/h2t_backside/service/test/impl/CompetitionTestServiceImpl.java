package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.filter.CompetitionTestFilterDTO;
import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import com.englishweb.h2t_backside.dto.test.TestPartDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.CompetitionTestMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.enummodel.TestPartEnum;
import com.englishweb.h2t_backside.model.test.CompetitionTest;
import com.englishweb.h2t_backside.repository.specifications.CompetitionTestSpecification;
import com.englishweb.h2t_backside.repository.test.CompetitionTestRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.CompetitionTestService;
import com.englishweb.h2t_backside.service.test.TestPartService;
import com.englishweb.h2t_backside.utils.BaseFilterSpecification;
import com.englishweb.h2t_backside.utils.ParseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CompetitionTestServiceImpl extends BaseServiceImpl<CompetitionTestDTO, CompetitionTest, CompetitionTestRepository> implements CompetitionTestService {
    private final CompetitionTestMapper mapper;
    private final TestPartService testPartService;
    public CompetitionTestServiceImpl(CompetitionTestRepository repository, DiscordNotifier discordNotifier, CompetitionTestMapper mapper, TestPartService testPartService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.testPartService = testPartService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("CompetitionTest with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    public CompetitionTestDTO create(CompetitionTestDTO dto) {

        List<Long> partIds = new ArrayList<>();
        for (TestPartEnum partType : List.of(
                TestPartEnum.VOCABULARY,
                TestPartEnum.GRAMMAR,
                TestPartEnum.READING,
                TestPartEnum.LISTENING,
                TestPartEnum.SPEAKING,
                TestPartEnum.WRITING
        )) {
            TestPartDTO part = new TestPartDTO();
            part.setType(partType);
            TestPartDTO savedPart = testPartService.create(part);
            partIds.add(savedPart.getId());
        }

        dto.setParts(partIds);
        return super.create(dto);
    }


    @Override
    protected void createError(CompetitionTestDTO dto, Exception ex) {
        log.error("Error creating competition test: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating competition test: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(CompetitionTestDTO dto, Long id, Exception ex) {
        log.error("Error updating competition test: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating competition test: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("CompetitionTest with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }


    @Override
    protected void patchEntityFromDTO(CompetitionTestDTO dto, CompetitionTest entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected CompetitionTest convertToEntity(CompetitionTestDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected CompetitionTestDTO convertToDTO(CompetitionTest entity) {
        return mapper.convertToDTO(entity);
    }
    @Override
    public Page<CompetitionTestDTO> searchWithFilters(int page, int size, String sortFields, CompetitionTestFilterDTO filter, Long userId) {
        Pageable pageable = ParseData.parsePageArgs(page, size, sortFields, CompetitionTest.class);

        Specification<CompetitionTest> specification = BaseFilterSpecification.applyBaseFilters(filter);

        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            specification = specification.and(CompetitionTestSpecification.findByName(filter.getTitle()));
        }

        if (filter.getStartStartTime() != null || filter.getEndStartTime() != null) {
            specification = specification.and(CompetitionTestSpecification.findByStartTimeRange(
                    filter.getStartStartTime(), filter.getEndStartTime()
            ));
        }

        if (filter.getStartEndTime() != null || filter.getEndEndTime() != null) {
            specification = specification.and(CompetitionTestSpecification.findByEndTimeRange(
                    filter.getStartEndTime(), filter.getEndEndTime()
            ));
        }

        return repository.findAll(specification, pageable).map(entity -> {
            CompetitionTestDTO dto = mapper.convertToDTO(entity);
            dto.setTotalQuestions(testPartService.countTotalQuestionsOfTest(dto.getParts()));
            return dto;
        });
    }

    @Override
    public CompetitionTestDTO getLastCompletedCompetition() {
        CompetitionTestFilterDTO filter = new CompetitionTestFilterDTO();
        filter.setEndEndTime(LocalDateTime.now());
        Page<CompetitionTestDTO> result = searchWithFilters(0, 1, "-endTime", filter, null);
        return result.getContent().get(0);
    }
}
