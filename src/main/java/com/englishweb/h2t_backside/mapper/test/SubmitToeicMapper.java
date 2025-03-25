package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicDTO;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicMapper {

    // Chuyển đổi từ SubmitToeicDTO sang SubmitToeic Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment")
    @Mapping(target = "toeic", source = "dto.toeic")
    @Mapping(target = "user", source = "dto.user")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitToeic convertToEntity(SubmitToeicDTO dto);

    // Chuyển đổi từ SubmitToeic Entity sang SubmitToeicDTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "comment", source = "entity.comment")
    @Mapping(target = "toeic", source = "entity.toeic")
    @Mapping(target = "user", source = "entity.user")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicDTO convertToDTO(SubmitToeic entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment")
    @Mapping(target = "toeic", source = "dto.toeic")
    @Mapping(target = "user", source = "dto.user")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicDTO dto, @MappingTarget SubmitToeic entity);
}
