package com.englishweb.h2t_backside.service.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.TopicDTO;
import com.englishweb.h2t_backside.exception.*;
import com.englishweb.h2t_backside.mapper.TopicMapper;
import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import com.englishweb.h2t_backside.model.lesson.Grammar;
import com.englishweb.h2t_backside.model.lesson.Topic;
import com.englishweb.h2t_backside.repository.lesson.TopicRepository;
import com.englishweb.h2t_backside.repository.specifications.BaseEntitySpecification;
import com.englishweb.h2t_backside.repository.specifications.LessonSpecification;
import com.englishweb.h2t_backside.service.DiscordNotifier;
import com.englishweb.h2t_backside.service.TopicService;
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
public class TopicServiceImpl extends BaseServiceImpl<TopicDTO, Topic, TopicRepository> implements TopicService {
    private final TopicMapper mapper;

    public TopicServiceImpl(TopicRepository repository, DiscordNotifier discordNotifier,TopicMapper mapper) {
        this.repository = repository;
        this.discordNotifier = discordNotifier;
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Topic with ID '%d' not found.", id);
        log.warn(errorMessage);

        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(TopicDTO dto, Exception ex) {
        log.error("Error creating entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;

        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(TopicDTO dto, Long id, Exception ex) {
        log.error("Error updating entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)){
            errorMessage = String.format("Topic with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(TopicDTO dto, Topic entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Topic convertToEntity(TopicDTO dto) {
        return Topic.builder()
                .id(dto.getId())
                .status(dto.getStatus() != null ? dto.getStatus() : StatusEnum.ACTIVE)
                .title(dto.getTitle())
                .image(dto.getImage())
                .description(dto.getDescription())
                .views(dto.getViews() != null ? dto.getViews() : 0L)
                .routeNode(dto.getRouteNode())
                .questions(dto.getQuestions())
                .build();
    }

    @Override
    protected TopicDTO convertToDTO(Topic entity) {
        return TopicDTO.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .title(entity.getTitle())
                .image(entity.getImage())
                .description(entity.getDescription())
                .views(entity.getViews())
                .routeNode(entity.getRouteNode())
                .questions(entity.getQuestions())
                .build();
    }


    @Override
    public Page<TopicDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter) {
        if (page < 0) {
            throw new InvalidArgumentException("Page index must not be less than 0.", page, ErrorApiCodeContent.PAGE_INDEX_INVALID);
        }

        if (size <= 0) {
            throw new InvalidArgumentException("Page size must be greater than 0.", size, ErrorApiCodeContent.PAGE_SIZE_INVALID);
        }

        Specification<Topic> specification = Specification.where(null);

        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            specification = specification.and(LessonSpecification.findByName(filter.getTitle()));
        }

        if (filter.getStatus() != null) {
            specification = specification.and(BaseEntitySpecification.hasStatus(filter.getStatus()));
        }

        if (filter.getStartCreatedAt() != null || filter.getEndCreatedAt() != null) {
            specification = specification.and(BaseEntitySpecification.findByCreatedAtRange(filter.getStartCreatedAt(), filter.getEndCreatedAt()));
        }

        if (filter.getStartCreatedAt() != null || filter.getEndUpdatedAt() != null) {
            specification = specification.and(BaseEntitySpecification.findByUpdatedAtRange(filter.getStartCreatedAt(), filter.getEndUpdatedAt()));
        }

        List<Sort.Order> orders = ParseData.parseStringToSortOrderList(sortFields);

        if(!ValidationData.isValidFieldInSortList(Grammar.class, orders)){
            throw new InvalidArgumentException("Invalid sort field.", sortFields, ErrorApiCodeContent.SORT_FIELD_INVALID);
        }

        Sort sort = Sort.by(orders);

        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findAll(specification, pageable).map(this::convertToDTO);
    }
}
