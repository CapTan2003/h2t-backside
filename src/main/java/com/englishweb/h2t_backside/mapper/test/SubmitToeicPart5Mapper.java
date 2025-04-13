package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicPart5DTO;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import com.englishweb.h2t_backside.model.test.SubmitToeicPart5;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitToeicPart5Mapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicId")
    @Mapping(target = "toeicPart5", source = "dto.toeicPart5Id", qualifiedByName = "mapToeicPart5Id")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitToeicPart5 convertToEntity(SubmitToeicPart5DTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitToeicId", source = "entity.submitToeic.id")
    @Mapping(target = "toeicPart5Id", source = "entity.toeicPart5.id")
    @Mapping(target = "answer", source = "entity.answer")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitToeicPart5DTO convertToDTO(SubmitToeicPart5 entity);

    // Patch DTO → Entity
    @Mapping(target = "submitToeic", source = "dto.submitToeicId", qualifiedByName = "mapSubmitToeicId")
    @Mapping(target = "toeicPart5", source = "dto.toeicPart5Id", qualifiedByName = "mapToeicPart5Id")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitToeicPart5DTO dto, @MappingTarget SubmitToeicPart5 entity);


    @Named("mapSubmitToeicId")
    default SubmitToeic mapSubmitToeicId(Long id) {
        if (id == null) return null;
        SubmitToeic entity = new SubmitToeic();
        entity.setId(id);
        return entity;
    }

    @Named("mapToeicPart5Id")
    default ToeicPart5 mapToeicPart5Id(Long id) {
        if (id == null) return null;
        ToeicPart5 entity = new ToeicPart5();
        entity.setId(id);
        return entity;
    }
}
