package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.filter.SubmitCompetitionFilterDTO;
import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionDTO;
import com.englishweb.h2t_backside.dto.test.SubmitTestDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.SubmitCompetitionMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.SubmitCompetition;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.repository.test.SubmitCompetitionRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.CompetitionTestService;
import com.englishweb.h2t_backside.service.test.SubmitCompetitionService;
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
public class SubmitCompetitionServiceImpl extends BaseServiceImpl<SubmitCompetitionDTO, SubmitCompetition, SubmitCompetitionRepository> implements SubmitCompetitionService {
    private final SubmitCompetitionMapper mapper;
    private final CompetitionTestService competitionTestService;

    public SubmitCompetitionServiceImpl(SubmitCompetitionRepository repository, DiscordNotifier discordNotifier, SubmitCompetitionMapper mapper, CompetitionTestService competitionTestService) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.competitionTestService = competitionTestService;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("SubmitCompetition with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
    }

    @Override
    protected void createError(SubmitCompetitionDTO dto, Exception ex) {
        log.error("Error creating submit competition: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating submit competition: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
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

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
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
    @Override
    public int countSubmitByUserId(Long userId) {
        return repository.countByUserIdAndStatusTrue(userId);
    }

    @Override
    public double totalScoreByUserId(Long userId) {
        return repository.sumScoreByUserIdAndStatusTrue(userId);
    }
    @Override
    public SubmitCompetitionDTO findByTestIdAndUserIdAndStatus(Long testId, Long userId, Boolean status) {
        SubmitCompetition submitTest = repository.findByTestIdAndUserIdAndStatus(testId, userId,status)
                .orElseThrow(() -> new ResourceNotFoundException(
                        testId,
                        String.format("Submit Comeptition with test ID '%d', userId '%d' and status=false not found.", testId, userId),
                        SeverityEnum.LOW
                ));
        return convertToDTO(submitTest);
    }
    @Override
    public Page<SubmitCompetitionDTO> searchWithFilters(int page, int size, String sortFields, SubmitCompetitionFilterDTO filter, Long userId) {
        Pageable pageable = ParseData.parsePageArgs(page, size, sortFields, SubmitCompetition.class);

        Specification<SubmitCompetition> spec = BaseFilterSpecification.applyBaseFilters(filter);

        if (userId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("user").get("id"), userId));
        }

        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.join("test").get("title")), "%" + filter.getTitle().toLowerCase() + "%"));
        }

        if (filter.getTestId() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("test").get("id"), filter.getTestId()));
        }

        return repository.findAll(spec, pageable)
                .map(mapper::convertToDTO);
    }

    @Override
    public List<SubmitCompetitionDTO> getLeaderboard() {
        CompetitionTestDTO lastCompetition = competitionTestService.getLastCompletedCompetition();
        Page<SubmitCompetitionDTO> submitPage = searchWithFilters(0, 5, "score", SubmitCompetitionFilterDTO.builder().testId(lastCompetition.getId()).build(), null);
        return submitPage.getContent();
    }
    @Override
    public List<SubmitCompetitionDTO> findByTestIdAndStatus(Long testId, Boolean status) {
        List<SubmitCompetition> entities = repository.findByTest_IdAndStatus(testId, status);

        return entities.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
