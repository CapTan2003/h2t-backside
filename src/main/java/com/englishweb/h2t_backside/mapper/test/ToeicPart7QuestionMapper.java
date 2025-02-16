package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart7QuestionDTO;
import com.englishweb.h2t_backside.model.test.ToeicPart7Question;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface ToeicPart7QuestionMapper {

    // Chuyển đổi từ ToeicPart7QuestionDTO sang ToeicPart7Question Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "answer1", source = "dto.answer1")
    @Mapping(target = "answer2", source = "dto.answer2")
    @Mapping(target = "answer3", source = "dto.answer3")
    @Mapping(target = "answer4", source = "dto.answer4")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    ToeicPart7Question convertToEntity(ToeicPart7QuestionDTO dto);

    // Chuyển đổi từ ToeicPart7Question Entity sang ToeicPart7QuestionDTO
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
    ToeicPart7QuestionDTO convertToDTO(ToeicPart7Question entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "answer1", source = "dto.answer1")
    @Mapping(target = "answer2", source = "dto.answer2")
    @Mapping(target = "answer3", source = "dto.answer3")
    @Mapping(target = "answer4", source = "dto.answer4")
    @Mapping(target = "correctAnswer", source = "dto.correctAnswer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(ToeicPart7QuestionDTO dto, @MappingTarget ToeicPart7Question entity);
}
