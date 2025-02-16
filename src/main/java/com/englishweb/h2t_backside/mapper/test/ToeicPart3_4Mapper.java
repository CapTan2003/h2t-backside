package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart3_4DTO;
import com.englishweb.h2t_backside.model.test.ToeicPart3_4;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicPart3_4Mapper {

    // Chuyển đổi từ ToeicPart3_4DTO sang ToeicPart3_4 Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "contentQuestion1", source = "dto.contentQuestion1")
    @Mapping(target = "contentQuestion2", source = "dto.contentQuestion2")
    @Mapping(target = "contentQuestion3", source = "dto.contentQuestion3")
    @Mapping(target = "answer1Q1", source = "dto.answer1Q1")
    @Mapping(target = "answer2Q1", source = "dto.answer2Q1")
    @Mapping(target = "answer3Q1", source = "dto.answer3Q1")
    @Mapping(target = "answer4Q1", source = "dto.answer4Q1")
    @Mapping(target = "answer1Q2", source = "dto.answer1Q2")
    @Mapping(target = "answer2Q2", source = "dto.answer2Q2")
    @Mapping(target = "answer3Q2", source = "dto.answer3Q2")
    @Mapping(target = "answer4Q2", source = "dto.answer4Q2")
    @Mapping(target = "answer1Q3", source = "dto.answer1Q3")
    @Mapping(target = "answer2Q3", source = "dto.answer2Q3")
    @Mapping(target = "answer3Q3", source = "dto.answer3Q3")
    @Mapping(target = "answer4Q3", source = "dto.answer4Q3")
    @Mapping(target = "correctAnswer1", source = "dto.correctAnswer1")
    @Mapping(target = "correctAnswer2", source = "dto.correctAnswer2")
    @Mapping(target = "correctAnswer3", source = "dto.correctAnswer3")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    ToeicPart3_4 convertToEntity(ToeicPart3_4DTO dto);

    // Chuyển đổi từ ToeicPart3_4 Entity sang ToeicPart3_4DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "audio", source = "entity.audio")
    @Mapping(target = "image", source = "entity.image")
    @Mapping(target = "contentQuestion1", source = "entity.contentQuestion1")
    @Mapping(target = "contentQuestion2", source = "entity.contentQuestion2")
    @Mapping(target = "contentQuestion3", source = "entity.contentQuestion3")
    @Mapping(target = "answer1Q1", source = "entity.answer1Q1")
    @Mapping(target = "answer2Q1", source = "entity.answer2Q1")
    @Mapping(target = "answer3Q1", source = "entity.answer3Q1")
    @Mapping(target = "answer4Q1", source = "entity.answer4Q1")
    @Mapping(target = "answer1Q2", source = "entity.answer1Q2")
    @Mapping(target = "answer2Q2", source = "entity.answer2Q2")
    @Mapping(target = "answer3Q2", source = "entity.answer3Q2")
    @Mapping(target = "answer4Q2", source = "entity.answer4Q2")
    @Mapping(target = "answer1Q3", source = "entity.answer1Q3")
    @Mapping(target = "answer2Q3", source = "entity.answer2Q3")
    @Mapping(target = "answer3Q3", source = "entity.answer3Q3")
    @Mapping(target = "answer4Q3", source = "entity.answer4Q3")
    @Mapping(target = "correctAnswer1", source = "entity.correctAnswer1")
    @Mapping(target = "correctAnswer2", source = "entity.correctAnswer2")
    @Mapping(target = "correctAnswer3", source = "entity.correctAnswer3")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    ToeicPart3_4DTO convertToDTO(ToeicPart3_4 entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "contentQuestion1", source = "dto.contentQuestion1")
    @Mapping(target = "contentQuestion2", source = "dto.contentQuestion2")
    @Mapping(target = "contentQuestion3", source = "dto.contentQuestion3")
    @Mapping(target = "answer1Q1", source = "dto.answer1Q1")
    @Mapping(target = "answer2Q1", source = "dto.answer2Q1")
    @Mapping(target = "answer3Q1", source = "dto.answer3Q1")
    @Mapping(target = "answer4Q1", source = "dto.answer4Q1")
    @Mapping(target = "answer1Q2", source = "dto.answer1Q2")
    @Mapping(target = "answer2Q2", source = "dto.answer2Q2")
    @Mapping(target = "answer3Q2", source = "dto.answer3Q2")
    @Mapping(target = "answer4Q2", source = "dto.answer4Q2")
    @Mapping(target = "answer1Q3", source = "dto.answer1Q3")
    @Mapping(target = "answer2Q3", source = "dto.answer2Q3")
    @Mapping(target = "answer3Q3", source = "dto.answer3Q3")
    @Mapping(target = "answer4Q3", source = "dto.answer4Q3")
    @Mapping(target = "correctAnswer1", source = "dto.correctAnswer1")
    @Mapping(target = "correctAnswer2", source = "dto.correctAnswer2")
    @Mapping(target = "correctAnswer3", source = "dto.correctAnswer3")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(ToeicPart3_4DTO dto, @MappingTarget ToeicPart3_4 entity);
}
