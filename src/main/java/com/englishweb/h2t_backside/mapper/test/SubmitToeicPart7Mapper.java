package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart7DTO;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart7;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicPart7Mapper {

    // Chuyển đổi từ SubmitToeicPart7DTO sang SubmitToeicPart7 Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitToeic", source = "dto.submitToeic")
    @Mapping(target = "toeicPart7Question", source = "dto.toeicPart7Question")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitToeicPart7 convertToEntity(SubmitToeicPart7DTO dto);

    // Chuyển đổi từ SubmitToeicPart7 Entity sang SubmitToeicPart7DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitToeic", source = "entity.submitToeic")
    @Mapping(target = "toeicPart7Question", source = "entity.toeicPart7Question")
    @Mapping(target = "answer", source = "entity.answer")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicPart7DTO convertToDTO(SubmitToeicPart7 entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "submitToeic", source = "dto.submitToeic")
    @Mapping(target = "toeicPart7Question", source = "dto.toeicPart7Question")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicPart7DTO dto, @MappingTarget SubmitToeicPart7 entity);
}
