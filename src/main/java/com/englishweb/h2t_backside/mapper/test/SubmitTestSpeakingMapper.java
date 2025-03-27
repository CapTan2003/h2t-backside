package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitTestSpeakingDTO;
import com.englishweb.h2t_backside.model.test.Question;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.model.test.SubmitTestSpeaking;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitTestSpeakingMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitTest", source = "dto.submitTestId", qualifiedByName = "mapSubmitTestId")
    @Mapping(target = "question", source = "dto.questionId", qualifiedByName = "mapQuestionId")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment")
    SubmitTestSpeaking convertToEntity(SubmitTestSpeakingDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitTestId", source = "entity.submitTest.id")
    @Mapping(target = "questionId", source = "entity.question.id")
    @Mapping(target = "transcript", source = "entity.transcript")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "comment", source = "entity.comment")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitTestSpeakingDTO convertToDTO(SubmitTestSpeaking entity);

    // Patch DTO → Entity
    @Mapping(target = "submitTest", source = "dto.submitTestId", qualifiedByName = "mapSubmitTestId")
    @Mapping(target = "question", source = "dto.questionId", qualifiedByName = "mapQuestionId")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment")
    void patchEntityFromDTO(SubmitTestSpeakingDTO dto, @MappingTarget SubmitTestSpeaking entity);


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
}
