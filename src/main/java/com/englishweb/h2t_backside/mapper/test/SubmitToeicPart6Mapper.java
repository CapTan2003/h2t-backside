package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart6DTO;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart6;
import com.englishweb.h2t_backside.model.test.ToeicPart6;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicPart6Mapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicId")
    @Mapping(target = "toeicPart6", source = "dto.toeicPart6Id", qualifiedByName = "mapToeicPart6Id")
    @Mapping(target = "answerQ1", source = "dto.answerQ1")
    @Mapping(target = "answerQ2", source = "dto.answerQ2")
    @Mapping(target = "answerQ3", source = "dto.answerQ3")
    @Mapping(target = "answerQ4", source = "dto.answerQ4")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitToeicPart6 convertToEntity(SubmitToeicPart6DTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitToeicId", source = "entity.submitToeic.id")
    @Mapping(target = "toeicPart6Id", source = "entity.toeicPart6.id")
    @Mapping(target = "answerQ1", source = "entity.answerQ1")
    @Mapping(target = "answerQ2", source = "entity.answerQ2")
    @Mapping(target = "answerQ3", source = "entity.answerQ3")
    @Mapping(target = "answerQ4", source = "entity.answerQ4")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicPart6DTO convertToDTO(SubmitToeicPart6 entity);

    // Patch DTO → Entity
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicId")
    @Mapping(target = "toeicPart6", source = "dto.toeicPart6Id", qualifiedByName = "mapToeicPart6Id")
    @Mapping(target = "answerQ1", source = "dto.answerQ1")
    @Mapping(target = "answerQ2", source = "dto.answerQ2")
    @Mapping(target = "answerQ3", source = "dto.answerQ3")
    @Mapping(target = "answerQ4", source = "dto.answerQ4")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicPart6DTO dto, @MappingTarget SubmitToeicPart6 entity);


    @Named("mapSubmitToeicId")
    default SubmitToeic mapSubmitToeicId(Long id) {
        if (id == null) return null;
        SubmitToeic entity = new SubmitToeic();
        entity.setId(id);
        return entity;
    }

    @Named("mapToeicPart6Id")
    default ToeicPart6 mapToeicPart6Id(Long id) {
        if (id == null) return null;
        ToeicPart6 entity = new ToeicPart6();
        entity.setId(id);
        return entity;
    }
}
