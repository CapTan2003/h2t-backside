package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.filter.CompetitionTestFilterDTO;
import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.CompetitionTestMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.CompetitionTest;
import com.englishweb.h2t_backside.repository.test.CompetitionTestRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.CompetitionTestService;
import com.englishweb.h2t_backside.service.test.TestPartService;
import com.englishweb.h2t_backside.utils.CompetitionTestPagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    public Page<CompetitionTestDTO> searchWithFilters(int page, int size, String sortFields, CompetitionTestFilterDTO filter, String userId) {
        return CompetitionTestPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, CompetitionTest.class
        ).map(entity -> {
            CompetitionTestDTO dto = mapper.convertToDTO(entity);
            dto.setTotalQuestions(testPartService.countTotalQuestionsOfTest(dto.getParts()));
            return dto;
        });
    }

}
