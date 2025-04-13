package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart7DTO;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart7;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicPart7Mapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicId")
    @Mapping(target = "toeicPart7Question", source = "dto.toeicPart7QuestionId", qualifiedByName = "mapPart7QuestionId")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitToeicPart7 convertToEntity(SubmitToeicPart7DTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitToeicId", source = "entity.submitToeic.id")
    @Mapping(target = "toeicPart7QuestionId", source = "entity.toeicPart7Question.id")
    @Mapping(target = "answer", source = "entity.answer")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicPart7DTO convertToDTO(SubmitToeicPart7 entity);

    // Patch DTO → Entity
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicId")
    @Mapping(target = "toeicPart7Question", source = "dto.toeicPart7QuestionId", qualifiedByName = "mapPart7QuestionId")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicPart7DTO dto, @MappingTarget SubmitToeicPart7 entity);


    @Named("mapSubmitToeicId")
    default SubmitToeic mapSubmitToeicId(Long id) {
        if (id == null) return null;
        SubmitToeic entity = new SubmitToeic();
        entity.setId(id);
        return entity;
    }

    @Named("mapPart7QuestionId")
    default ToeicPart7Question mapPart7QuestionId(Long id) {
        if (id == null) return null;
        ToeicPart7Question entity = new ToeicPart7Question();
        entity.setId(id);
        return entity;
    }
}
