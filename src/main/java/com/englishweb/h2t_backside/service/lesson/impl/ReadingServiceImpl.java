package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.ReadingDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.ReadingMapper;
import com.englishweb.h2t_backside.model.lesson.Reading;
import com.englishweb.h2t_backside.repository.lesson.ReadingRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.ReadingService;
import com.englishweb.h2t_backside.utils.LessonPagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReadingServiceImpl extends BaseServiceImpl<ReadingDTO, Reading, ReadingRepository> implements ReadingService {

    private final ReadingMapper mapper;

    public ReadingServiceImpl(ReadingRepository repository, DiscordNotifier discordNotifier, ReadingMapper mapper) {
        this.repository = repository;
        this.discordNotifier = discordNotifier;
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Reading with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(ReadingDTO dto, Exception ex) {
        log.error("Error creating reading: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating reading: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(ReadingDTO dto, Long id, Exception ex) {
        log.error("Error updating reading: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating reading: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Reading with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(ReadingDTO dto, Reading entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Reading convertToEntity(ReadingDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ReadingDTO convertToDTO(Reading entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public Page<ReadingDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        return LessonPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, Reading.class
        ).map(this::convertToDTO);
    }
}