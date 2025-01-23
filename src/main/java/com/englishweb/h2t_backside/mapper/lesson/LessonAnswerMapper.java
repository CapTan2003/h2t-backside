package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.LessonAnswerDTO;
import com.englishweb.h2t_backside.model.lesson.LessonAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LessonAnswerMapper {

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "correct", source = "dto.correct")
    @Mapping(target = "questionId", source = "dto.questionId")
    LessonAnswer convertToEntity(LessonAnswerDTO dto);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "correct", source = "entity.correct")
    @Mapping(target = "questionId", source = "entity.questionId")
    LessonAnswerDTO convertToDTO(LessonAnswer entity);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "correct", source = "dto.correct")
    @Mapping(target = "questionId", source = "dto.questionId")
    void patchEntityFromDTO(LessonAnswerDTO dto, @MappingTarget LessonAnswer entity);
}
