package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import com.englishweb.h2t_backside.model.test.CompetitionTest;
import com.englishweb.h2t_backside.utils.ParseData;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface CompetitionTestMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "startTime", source = "dto.startTime")
    @Mapping(target = "endTime", source = "dto.endTime")
    @Mapping(target = "parts", source = "dto.parts", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    CompetitionTest convertToEntity(CompetitionTestDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "duration", source = "entity.duration")
    @Mapping(target = "startTime", source = "entity.startTime")
    @Mapping(target = "endTime", source = "entity.endTime")
    @Mapping(target = "parts", source = "entity.parts", qualifiedByName = "stringToLongList")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    CompetitionTestDTO convertToDTO(CompetitionTest entity);

    // Patch DTO → Entity
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "startTime", source = "dto.startTime")
    @Mapping(target = "endTime", source = "dto.endTime")
    @Mapping(target = "parts", source = "dto.parts", qualifiedByName = "longListToString")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(CompetitionTestDTO dto, @MappingTarget CompetitionTest entity);

    // Hàm convert thủ công
    @Named("stringToLongList")
    default List<Long> stringToLongList(String str) {
        return ParseData.parseStringToLongList(str);
    }

    @Named("longListToString")
    default String longListToString(List<Long> list) {
        return ParseData.parseLongListToString(list);
    }
}
