package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitTestAnswerDTO;
import com.englishweb.h2t_backside.model.test.Answer;
import com.englishweb.h2t_backside.model.test.Question;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.model.test.SubmitTestAnswer;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitTestAnswerMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitTest", source = "dto.submitTestId", qualifiedByName = "mapSubmitTestId")
    @Mapping(target = "question", source = "dto.questionId", qualifiedByName = "mapQuestionId")
    @Mapping(target = "answer", source = "dto.answerId", qualifiedByName = "mapAnswerId")
    SubmitTestAnswer convertToEntity(SubmitTestAnswerDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitTestId", source = "entity.submitTest.id")
    @Mapping(target = "questionId", source = "entity.question.id")
    @Mapping(target = "answerId", source = "entity.answer.id")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitTestAnswerDTO convertToDTO(SubmitTestAnswer entity);

    // Patch DTO → Entity
    @Mapping(target = "submitTest", source = "dto.submitTestId", qualifiedByName = "mapSubmitTestId")
    @Mapping(target = "question", source = "dto.questionId", qualifiedByName = "mapQuestionId")
    @Mapping(target = "answer", source = "dto.answerId", qualifiedByName = "mapAnswerId")
    void patchEntityFromDTO(SubmitTestAnswerDTO dto, @MappingTarget SubmitTestAnswer entity);


    @Named("mapSubmitTestId")
    default SubmitTest mapSubmitTestId(Long id) {
        if (id == null) return null;
        SubmitTest entity = new SubmitTest();
        entity.setId(id);
        return entity;
    }

    @Named("mapQuestionId")
    default Question mapQuestionId(Long id) {
        if (id == null) return null;
        Question entity = new Question();
        entity.setId(id);
        return entity;
    }

    @Named("mapAnswerId")
    default Answer mapAnswerId(Long id) {
        if (id == null) return null;
        Answer entity = new Answer();
        entity.setId(id);
        return entity;
    }
}
