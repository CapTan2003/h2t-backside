package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.model.test.Test;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TestMapper {

    // Chuyển đổi từ TestDTO sang Test Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "parts", source = "dto.parts")
    @Mapping(target = "routeNode", source = "dto.routeNode")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    Test convertToEntity(TestDTO dto);

    // Chuyển đổi từ Test Entity sang TestDTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "duration", source = "entity.duration")
    @Mapping(target = "type", source = "entity.type")
    @Mapping(target = "parts", source = "entity.parts")
    @Mapping(target = "routeNode", source = "entity.routeNode")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    TestDTO convertToDTO(Test entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "parts", source = "dto.parts")
    @Mapping(target = "routeNode", source = "dto.routeNode")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(TestDTO dto, @MappingTarget Test entity);
}
