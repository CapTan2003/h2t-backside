package com.englishweb.h2t_backside.service.impl;

import com.englishweb.h2t_backside.dto.lesson.GrammarDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import com.englishweb.h2t_backside.model.lesson.Grammar;
import com.englishweb.h2t_backside.repository.lesson.GrammarRepository;
import com.englishweb.h2t_backside.service.DiscordNotifier;
import com.englishweb.h2t_backside.service.GrammarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GrammarServiceImpl extends BaseServiceImpl<GrammarDTO, Grammar, GrammarRepository> implements GrammarService {

    public GrammarServiceImpl(GrammarRepository repository, DiscordNotifier discordNotifier) {
        this.repository = repository;
        this.discordNotifier = discordNotifier;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Grammar with ID '%d' not found.", id);
        log.warn(errorMessage);

        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(GrammarDTO dto, Exception ex) {
        log.error("Error creating entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;

        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(GrammarDTO dto, Long id, Exception ex) {
        log.error("Error updating entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)){
            errorMessage = String.format("Grammar with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(GrammarDTO dto, Grammar entity) {
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getImage() != null) {
            entity.setImage(dto.getImage());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getViews() != null) {
            entity.setViews(dto.getViews());
        }
        if (dto.getRouteNode() != null) {
            entity.setRouteNode(dto.getRouteNode());
        }
        if (dto.getFile() != null) {
            entity.setFile(dto.getFile());
        }
        if (dto.getDefinition() != null) {
            entity.setDefinition(dto.getDefinition());
        }
        if (dto.getExample() != null) {
            entity.setExample(dto.getExample());
        }
        if (dto.getQuestions() != null) {
            entity.setQuestions(dto.getQuestions());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
    }

    @Override
    protected Grammar convertToEntity(GrammarDTO dto) {
        return Grammar.builder()
                .id(dto.getId())
                .status(dto.getStatus() != null ? dto.getStatus() : StatusEnum.ACTIVE)
                .title(dto.getTitle())
                .image(dto.getImage())
                .description(dto.getDescription())
                .views(dto.getViews() != null ? dto.getViews() : 0L)
                .routeNode(dto.getRouteNode())
                .questions(dto.getQuestions())
                .definition(dto.getDefinition())
                .example(dto.getExample())
                .file(dto.getFile())
                .build();
    }

    @Override
    protected GrammarDTO convertToDTO(Grammar entity) {
        return GrammarDTO.builder()
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
                .definition(entity.getDefinition())
                .example(entity.getExample())
                .file(entity.getFile())
                .build();
    }
}
