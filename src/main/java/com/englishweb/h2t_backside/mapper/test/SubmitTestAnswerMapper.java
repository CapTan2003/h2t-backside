package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitTestAnswerDTO;
import com.englishweb.h2t_backside.model.test.SubmitTestAnswer;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {SubmitTestMapper.class, QuestionMapper.class, AnswerMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface SubmitTestAnswerMapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitTest", source = "dto.submitTest")
    @Mapping(target = "question", source = "dto.question")
    @Mapping(target = "answer", source = "dto.answer")
    SubmitTestAnswer convertToEntity(SubmitTestAnswerDTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitTest", source = "entity.submitTest")
    @Mapping(target = "question", source = "entity.question")
    @Mapping(target = "answer", source = "entity.answer")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitTestAnswerDTO convertToDTO(SubmitTestAnswer entity);

    // Cập nhật dữ liệu từ DTO vào Entity (bỏ qua trường null)
    @Mapping(target = "submitTest", source = "dto.submitTest")
    @Mapping(target = "question", source = "dto.question")
    @Mapping(target = "answer", source = "dto.answer")
    void patchEntityFromDTO(SubmitTestAnswerDTO dto, @MappingTarget SubmitTestAnswer entity);
}
