package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.VocabularyFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.VocabularyDTO;
import com.englishweb.h2t_backside.exception.*;
import com.englishweb.h2t_backside.mapper.lesson.VocabularyMapper;
import com.englishweb.h2t_backside.model.lesson.Vocabulary;
import com.englishweb.h2t_backside.repository.lesson.VocabularyRepository;
import com.englishweb.h2t_backside.repository.specifications.BaseEntitySpecification;
import com.englishweb.h2t_backside.repository.specifications.VocabularySpecification;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.VocabularyService;
import com.englishweb.h2t_backside.utils.BaseFilterSpecification;
import com.englishweb.h2t_backside.utils.ParseData;
import com.englishweb.h2t_backside.utils.ValidationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VocabularyServiceImpl extends BaseServiceImpl<VocabularyDTO, Vocabulary, VocabularyRepository> implements VocabularyService {

    private final VocabularyMapper mapper;

    public VocabularyServiceImpl(VocabularyRepository repository,
                                 DiscordNotifier discordNotifier,
                                 VocabularyMapper mapper) {
        this.repository = repository;
        this.discordNotifier = discordNotifier;
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Vocabulary with ID '%d' not found", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(VocabularyDTO dto, Exception ex) {
        log.error("Error creating vocabulary: {}", ex.getMessage());
        String errorMessage = "Failed to create vocabulary: " + ex.getMessage();
        throw new CreateResourceException(
                dto,
                errorMessage,
                ErrorApiCodeContent.VOCABULARY_CREATED_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @Override
    protected void updateError(VocabularyDTO dto, Long id, Exception ex) {
        log.error("Error updating vocabulary ID {}: {}", id, ex.getMessage());
        String errorMessage = "Failed to update vocabulary: " + ex.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Vocabulary with ID '%d' not found", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(
                dto,
                errorMessage,
                ErrorApiCodeContent.VOCABULARY_UPDATED_FAIL,
                status
        );
    }

    @Override
    protected void patchEntityFromDTO(VocabularyDTO dto, Vocabulary entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Vocabulary convertToEntity(VocabularyDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected VocabularyDTO convertToDTO(Vocabulary entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<VocabularyDTO> searchWithFilters(int page, int size, String sortFields, VocabularyFilterDTO filter) {
        if (page < 0) {
            throw new InvalidArgumentException("Page index must not be less than 0.", page, ErrorApiCodeContent.PAGE_INDEX_INVALID);
        }

        if (size <= 0) {
            throw new InvalidArgumentException("Page size must be greater than 0.", size, ErrorApiCodeContent.PAGE_SIZE_INVALID);
        }

        List<Sort.Order> orders = ParseData.parseStringToSortOrderList(sortFields);

        if (!ValidationData.isValidFieldInSortList(Vocabulary.class, orders)) {
            throw new InvalidArgumentException("Invalid sort field.", sortFields, ErrorApiCodeContent.SORT_FIELD_INVALID);
        }

        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Vocabulary> specification = Specification.where(BaseFilterSpecification.applyBaseFilters(filter));

        if (filter.getWord() != null && !filter.getWord().isEmpty()) {
            specification = specification.and(VocabularySpecification.findByWord(filter.getWord()));
        }

        if (filter.getWordType() != null) {
            specification = specification.and(VocabularySpecification.findByWordType(filter.getWordType()));
        }

        return repository.findAll(specification, pageable).map(this::convertToDTO);
    }
}