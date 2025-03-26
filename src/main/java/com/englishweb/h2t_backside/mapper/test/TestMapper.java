package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.model.test.Test;
import com.englishweb.h2t_backside.model.RouteNode;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TestMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "parts", source = "dto.parts")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    Test convertToEntity(TestDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "duration", source = "entity.duration")
    @Mapping(target = "type", source = "entity.type")
    @Mapping(target = "parts", source = "entity.parts")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    TestDTO convertToDTO(Test entity);

    // Patch DTO → Entity
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "parts", source = "dto.parts")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(TestDTO dto, @MappingTarget Test entity);

}
