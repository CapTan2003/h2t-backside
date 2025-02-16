package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart1DTO;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart1;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicPart1Mapper {

    // Chuyển đổi từ SubmitToeicPart1DTO sang SubmitToeicPart1 Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitToeic", source = "dto.submitToeic")
    @Mapping(target = "toeicPart1", source = "dto.toeicPart1")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    SubmitToeicPart1 convertToEntity(SubmitToeicPart1DTO dto);

    // Chuyển đổi từ SubmitToeicPart1 Entity sang SubmitToeicPart1DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitToeic", source = "entity.submitToeic")
    @Mapping(target = "toeicPart1", source = "entity.toeicPart1")
    @Mapping(target = "answer", source = "entity.answer")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicPart1DTO convertToDTO(SubmitToeicPart1 entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "submitToeic", source = "dto.submitToeic")
    @Mapping(target = "toeicPart1", source = "dto.toeicPart1")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicPart1DTO dto, @MappingTarget SubmitToeicPart1 entity);
}
