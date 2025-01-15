package com.englishweb.h2t_backside.service.impl;

import com.englishweb.h2t_backside.dto.lesson.GrammarDTO;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import com.englishweb.h2t_backside.model.lesson.Grammar;
import com.englishweb.h2t_backside.repository.lesson.GrammarRepository;
import com.englishweb.h2t_backside.service.GrammarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GrammarServiceImpl extends BaseServiceImpl<GrammarDTO, Grammar, GrammarRepository> implements GrammarService {

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Grammar with ID '%d' not found.", id);
        log.warn(errorMessage);

        this.discordNotifier.buildErrorAndSend(id, errorMessage, ErrorApiCodeContent.LESSON_NOT_FOUND);
    }

    @Override
    protected void createError(GrammarDTO dto, Exception ex) {
        log.error("Error creating entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;

    }

    @Override
    protected void updateError(GrammarDTO dto, Long id, Exception ex) {

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
