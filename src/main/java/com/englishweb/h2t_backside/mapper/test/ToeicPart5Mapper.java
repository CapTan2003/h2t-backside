package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart5DTO;
import com.englishweb.h2t_backside.model.test.ToeicPart5;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicPart5Mapper {

    // Chuyển đổi từ ToeicPart5DTO sang ToeicPart5 Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "answer1", source = "dto.answer1")
    @Mapping(target = "answer2", source = "dto.answer2")
    @Mapping(target = "answer3", source = "dto.answer3")
    @Mapping(target = "answer4", source = "dto.answer4")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    ToeicPart5 convertToEntity(ToeicPart5DTO dto);

    // Chuyển đổi từ ToeicPart5 Entity sang ToeicPart5DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "answer1", source = "entity.answer1")
    @Mapping(target = "answer2", source = "entity.answer2")
    @Mapping(target = "answer3", source = "entity.answer3")
    @Mapping(target = "answer4", source = "entity.answer4")
    @Mapping(target = "correctAnswer", source = "entity.correctAnswer")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    ToeicPart5DTO convertToDTO(ToeicPart5 entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "answer1", source = "dto.answer1")
    @Mapping(target = "answer2", source = "dto.answer2")
    @Mapping(target = "answer3", source = "dto.answer3")
    @Mapping(target = "answer4", source = "dto.answer4")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(ToeicPart5DTO dto, @MappingTarget ToeicPart5 entity);
}
