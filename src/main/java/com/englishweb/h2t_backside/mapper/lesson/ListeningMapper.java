package com.englishweb.h2t_backside.mapper.lesson;

import com.englishweb.h2t_backside.dto.lesson.ListeningDTO;
import com.englishweb.h2t_backside.mapper.RouteNodeMapper;
import com.englishweb.h2t_backside.model.lesson.Listening;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PreparationMapper.class, RouteNodeMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface ListeningMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "views", source = "dto.views", defaultValue = "0L")
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "preparation", source = "dto.preparation")
    @Mapping(target = "routeNode", source = "dto.routeNode")
    Listening convertToEntity(ListeningDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "image", source = "entity.image")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "views", source = "entity.views")
    @Mapping(target = "audio", source = "entity.audio")
    @Mapping(target = "transcript", source = "entity.transcript")
    @Mapping(target = "questions", source = "entity.questions")
    @Mapping(target = "preparation", source = "entity.preparation")
    @Mapping(target = "routeNode", source = "entity.routeNode")
    ListeningDTO convertToDTO(Listening entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "views", source = "dto.views")
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "preparation", source = "dto.preparation")
    @Mapping(target = "routeNode", source = "dto.routeNode")
    void patchEntityFromDTO(ListeningDTO dto, @MappingTarget Listening entity);
}
