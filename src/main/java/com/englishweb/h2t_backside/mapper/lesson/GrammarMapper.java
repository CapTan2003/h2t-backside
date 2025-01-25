package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.GrammarDTO;
import com.englishweb.h2t_backside.mapper.RouteNodeMapper;
import com.englishweb.h2t_backside.model.lesson.Grammar;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = RouteNodeMapper.class,
        builder = @Builder(disableBuilder = true)
)
public interface GrammarMapper {

    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    @Mapping(target = "views", source = "dto.views", defaultValue = "0L")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "routeNode", source = "dto.routeNode")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "definition", source = "dto.definition")
    @Mapping(target = "example", source = "dto.example")
    @Mapping(target = "file", source = "dto.file")
    Grammar convertToEntity(GrammarDTO dto);

    // Convert Grammar entity to GrammarDTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "image", source = "entity.image")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "views", source = "entity.views")
    @Mapping(target = "routeNode", source = "entity.routeNode")
    @Mapping(target = "questions", source = "entity.questions")
    @Mapping(target = "definition", source = "entity.definition")
    @Mapping(target = "example", source = "entity.example")
    @Mapping(target = "file", source = "entity.file")
    GrammarDTO convertToDTO(Grammar entity);

    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "views", source = "dto.views")
    @Mapping(target = "routeNode", source = "dto.routeNode")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "definition", source = "dto.definition")
    @Mapping(target = "example", source = "dto.example")
    @Mapping(target = "file", source = "dto.file")
    void patchEntityFromDTO(GrammarDTO dto, @MappingTarget Grammar entity);
}