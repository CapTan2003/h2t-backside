package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart3_4DTO;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart3_4;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicPart3_4Mapper {

    // Chuyển đổi từ SubmitToeicPart3_4DTO sang SubmitToeicPart3_4 Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitToeic", source = "dto.submitToeic")
    @Mapping(target = "toeicPart3_4", source = "dto.toeicPart3_4")
    @Mapping(target = "answerQ1", source = "dto.answerQ1")
    @Mapping(target = "answerQ2", source = "dto.answerQ2")
    @Mapping(target = "answerQ3", source = "dto.answerQ3")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    SubmitToeicPart3_4 convertToEntity(SubmitToeicPart3_4DTO dto);

    // Chuyển đổi từ SubmitToeicPart3_4 Entity sang SubmitToeicPart3_4DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitToeic", source = "entity.submitToeic")
    @Mapping(target = "toeicPart3_4", source = "entity.toeicPart3_4")
    @Mapping(target = "answerQ1", source = "entity.answerQ1")
    @Mapping(target = "answerQ2", source = "entity.answerQ2")
    @Mapping(target = "answerQ3", source = "entity.answerQ3")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicPart3_4DTO convertToDTO(SubmitToeicPart3_4 entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "submitToeic", source = "dto.submitToeic")
    @Mapping(target = "toeicPart3_4", source = "dto.toeicPart3_4")
    @Mapping(target = "answerQ1", source = "dto.answerQ1")
    @Mapping(target = "answerQ2", source = "dto.answerQ2")
    @Mapping(target = "answerQ3", source = "dto.answerQ3")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicPart3_4DTO dto, @MappingTarget SubmitToeicPart3_4 entity);
}
