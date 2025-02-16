package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart7DTO;
import com.englishweb.h2t_backside.model.test.ToeicPart7;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicPart7Mapper {

    // Chuyển đổi từ ToeicPart7DTO sang ToeicPart7 Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    ToeicPart7 convertToEntity(ToeicPart7DTO dto);

    // Chuyển đổi từ ToeicPart7 Entity sang ToeicPart7DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "file", source = "entity.file")
    @Mapping(target = "questions", source = "entity.questions")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    ToeicPart7DTO convertToDTO(ToeicPart7 entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(ToeicPart7DTO dto, @MappingTarget ToeicPart7 entity);
}
