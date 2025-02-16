package com.englishweb.h2t_backside.mapper.test;
import com.englishweb.h2t_backside.dto.test.AnswerDTO;
import com.englishweb.h2t_backside.model.test.Answer;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface AnswerMapper {

    // Chuyển đổi từ AnswerDTO sang Answer Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "correct", source = "dto.correct")
    @Mapping(target = "question", source = "dto.question")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    Answer convertToEntity(AnswerDTO dto);

    // Chuyển đổi từ Answer Entity sang AnswerDTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "correct", source = "entity.correct")
    @Mapping(target = "question", source = "entity.question")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    AnswerDTO convertToDTO(Answer entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "correct", source = "dto.correct")
    @Mapping(target = "question", source = "dto.question")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(AnswerDTO dto, @MappingTarget Answer entity);
}
