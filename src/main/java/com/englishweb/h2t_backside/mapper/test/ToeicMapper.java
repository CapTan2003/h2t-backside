package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicDTO;
import com.englishweb.h2t_backside.model.test.Toeic;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicMapper {

    // Chuyển đổi từ ToeicDTO sang Toeic Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "questionsPart1", source = "dto.questionsPart1")
    @Mapping(target = "questionsPart2", source = "dto.questionsPart2")
    @Mapping(target = "questionsPart3", source = "dto.questionsPart3")
    @Mapping(target = "questionsPart4", source = "dto.questionsPart4")
    @Mapping(target = "questionsPart5", source = "dto.questionsPart5")
    @Mapping(target = "questionsPart6", source = "dto.questionsPart6")
    @Mapping(target = "questionsPart7", source = "dto.questionsPart7")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    Toeic convertToEntity(ToeicDTO dto);

    // Chuyển đổi từ Toeic Entity sang ToeicDTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "duration", source = "entity.duration")
    @Mapping(target = "questionsPart1", source = "entity.questionsPart1")
    @Mapping(target = "questionsPart2", source = "entity.questionsPart2")
    @Mapping(target = "questionsPart3", source = "entity.questionsPart3")
    @Mapping(target = "questionsPart4", source = "entity.questionsPart4")
    @Mapping(target = "questionsPart5", source = "entity.questionsPart5")
    @Mapping(target = "questionsPart6", source = "entity.questionsPart6")
    @Mapping(target = "questionsPart7", source = "entity.questionsPart7")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    ToeicDTO convertToDTO(Toeic entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "duration", source = "dto.duration")
    @Mapping(target = "questionsPart1", source = "dto.questionsPart1")
    @Mapping(target = "questionsPart2", source = "dto.questionsPart2")
    @Mapping(target = "questionsPart3", source = "dto.questionsPart3")
    @Mapping(target = "questionsPart4", source = "dto.questionsPart4")
    @Mapping(target = "questionsPart5", source = "dto.questionsPart5")
    @Mapping(target = "questionsPart6", source = "dto.questionsPart6")
    @Mapping(target = "questionsPart7", source = "dto.questionsPart7")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(ToeicDTO dto, @MappingTarget Toeic entity);
}
