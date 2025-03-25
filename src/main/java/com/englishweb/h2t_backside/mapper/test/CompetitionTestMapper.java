package com.englishweb.h2t_backside.mapper.test;
import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import com.englishweb.h2t_backside.model.test.CompetitionTest;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface CompetitionTestMapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "startTime", source = "dto.startTime")
    @Mapping(target = "endTime", source = "dto.endTime")
    @Mapping(target = "parts", source = "dto.parts", defaultValue = "")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    CompetitionTest convertToEntity(CompetitionTestDTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "duration", source = "entity.duration")
    @Mapping(target = "startTime", source = "entity.startTime")
    @Mapping(target = "endTime", source = "entity.endTime")
    @Mapping(target = "parts", source = "entity.parts")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    CompetitionTestDTO convertToDTO(CompetitionTest entity);

    // Cập nhật dữ liệu từ DTO vào Entity (bỏ qua trường null)
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "startTime", source = "dto.startTime")
    @Mapping(target = "endTime", source = "dto.endTime")
    @Mapping(target = "parts", source = "dto.parts")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(CompetitionTestDTO dto, @MappingTarget CompetitionTest entity);
}
