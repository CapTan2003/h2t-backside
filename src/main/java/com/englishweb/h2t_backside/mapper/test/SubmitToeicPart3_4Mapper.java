package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart3_4DTO;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart3_4;
import com.englishweb.h2t_backside.model.test.ToeicPart3_4;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicPart3_4Mapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicId")
    @Mapping(target = "toeicPart3_4", source = "dto.toeicPart3_4Id", qualifiedByName = "mapToeicPart3_4Id")
    @Mapping(target = "answerQ1", source = "dto.answerQ1")
    @Mapping(target = "answerQ2", source = "dto.answerQ2")
    @Mapping(target = "answerQ3", source = "dto.answerQ3")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitToeicPart3_4 convertToEntity(SubmitToeicPart3_4DTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitToeicId", source = "entity.submitToeic.id")
    @Mapping(target = "toeicPart3_4Id", source = "entity.toeicPart3_4.id")
    @Mapping(target = "answerQ1", source = "entity.answerQ1")
    @Mapping(target = "answerQ2", source = "entity.answerQ2")
    @Mapping(target = "answerQ3", source = "entity.answerQ3")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicPart3_4DTO convertToDTO(SubmitToeicPart3_4 entity);

    // Patch DTO → Entity
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicId")
    @Mapping(target = "toeicPart3_4", source = "dto.toeicPart3_4Id", qualifiedByName = "mapToeicPart3_4Id")
    @Mapping(target = "answerQ1", source = "dto.answerQ1")
    @Mapping(target = "answerQ2", source = "dto.answerQ2")
    @Mapping(target = "answerQ3", source = "dto.answerQ3")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicPart3_4DTO dto, @MappingTarget SubmitToeicPart3_4 entity);


    @Named("mapSubmitToeicId")
    default SubmitToeic mapSubmitToeicId(Long id) {
        if (id == null) return null;
        SubmitToeic entity = new SubmitToeic();
        entity.setId(id);
        return entity;
    }

    @Named("mapToeicPart3_4Id")
    default ToeicPart3_4 mapToeicPart3_4Id(Long id) {
        if (id == null) return null;
        ToeicPart3_4 entity = new ToeicPart3_4();
        entity.setId(id);
        return entity;
    }
}
