package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart5DTO;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart5;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicPart5Mapper {

    // Chuyển đổi từ SubmitToeicPart5DTO sang SubmitToeicPart5 Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitToeic", source = "dto.submitToeic")
    @Mapping(target = "toeicPart5", source = "dto.toeicPart5")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    SubmitToeicPart5 convertToEntity(SubmitToeicPart5DTO dto);

    // Chuyển đổi từ SubmitToeicPart5 Entity sang SubmitToeicPart5DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitToeic", source = "entity.submitToeic")
    @Mapping(target = "toeicPart5", source = "entity.toeicPart5")
    @Mapping(target = "answer", source = "entity.answer")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicPart5DTO convertToDTO(SubmitToeicPart5 entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "submitToeic", source = "dto.submitToeic")
    @Mapping(target = "toeicPart5", source = "dto.toeicPart5")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicPart5DTO dto, @MappingTarget SubmitToeicPart5 entity);
}
