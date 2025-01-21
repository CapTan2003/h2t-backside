package com.englishweb.h2t_backside.mapper;

import com.englishweb.h2t_backside.dto.lesson.GrammarDTO;
import com.englishweb.h2t_backside.model.lesson.Grammar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GrammarMapper {

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