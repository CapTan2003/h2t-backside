package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.WritingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.WritingMapper;
import com.englishweb.h2t_backside.model.lesson.Writing;
import com.englishweb.h2t_backside.repository.lesson.WritingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.WritingService;
import com.englishweb.h2t_backside.utils.LessonPagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WritingServiceImpl extends BaseServiceImpl<WritingDTO, Writing, WritingRepository> implements WritingService {

    private final WritingMapper mapper;

    public WritingServiceImpl(WritingRepository repository, DiscordNotifier discordNotifier, WritingMapper mapper) {
        this.repository = repository;
        this.discordNotifier = discordNotifier;
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Writing with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(WritingDTO dto, Exception ex) {
        log.error("Error creating writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(WritingDTO dto, Long id, Exception ex) {
        log.error("Error updating writing: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating writing: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Writing with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(WritingDTO dto, Writing entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Writing convertToEntity(WritingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected WritingDTO convertToDTO(Writing entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<WritingDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        Specification<Writing> specification = Specification.where(null);
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, specification, Writing.class
        ).map(this::convertToDTO);
    }
}