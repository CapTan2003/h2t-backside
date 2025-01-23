package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.TopicDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.TopicMapper;
import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import com.englishweb.h2t_backside.model.lesson.Topic;
import com.englishweb.h2t_backside.repository.lesson.TopicRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.TopicService;
import com.englishweb.h2t_backside.utils.LessonPagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        Specification<Topic> specification = Specification.where(null);
        return LessonPagination.searchWithFiltersGeneric(page, size, sortFields, filter, repository, specification, Topic.class).map(this::convertToDTO);
    }
}
