package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart6DTO;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart6;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicPart6Mapper {

    // Chuyển đổi từ SubmitToeicPart6DTO sang SubmitToeicPart6 Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitToeic", source = "dto.submitToeic")
    @Mapping(target = "toeicPart6", source = "dto.toeicPart6")
    @Mapping(target = "answerQ1", source = "dto.answerQ1")
    @Mapping(target = "answerQ2", source = "dto.answerQ2")
    @Mapping(target = "answerQ3", source = "dto.answerQ3")
    @Mapping(target = "answerQ4", source = "dto.answerQ4")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitToeicPart6 convertToEntity(SubmitToeicPart6DTO dto);

    // Chuyển đổi từ SubmitToeicPart6 Entity sang SubmitToeicPart6DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitToeic", source = "entity.submitToeic")
    @Mapping(target = "toeicPart6", source = "entity.toeicPart6")
    @Mapping(target = "answerQ1", source = "entity.answerQ1")
    @Mapping(target = "answerQ2", source = "entity.answerQ2")
    @Mapping(target = "answerQ3", source = "entity.answerQ3")
    @Mapping(target = "answerQ4", source = "entity.answerQ4")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicPart6DTO convertToDTO(SubmitToeicPart6 entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "submitToeic", source = "dto.submitToeic")
    @Mapping(target = "toeicPart6", source = "dto.toeicPart6")
    @Mapping(target = "answerQ1", source = "dto.answerQ1")
    @Mapping(target = "answerQ2", source = "dto.answerQ2")
    @Mapping(target = "answerQ3", source = "dto.answerQ3")
    @Mapping(target = "answerQ4", source = "dto.answerQ4")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicPart6DTO dto, @MappingTarget SubmitToeicPart6 entity);
}
