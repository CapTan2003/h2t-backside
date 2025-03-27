package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitTestWritingDTO;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.model.test.SubmitTestWriting;
import com.englishweb.h2t_backside.model.test.TestWriting;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitTestWritingMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitTest", source = "dto.submitTestId", qualifiedByName = "mapSubmitTestId")
    @Mapping(target = "testWriting", source = "dto.testWritingId", qualifiedByName = "mapTestWritingId")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment")
    SubmitTestWriting convertToEntity(SubmitTestWritingDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitTestId", source = "entity.submitTest.id")
    @Mapping(target = "testWritingId", source = "entity.testWriting.id")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "comment", source = "entity.comment")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitTestWritingDTO convertToDTO(SubmitTestWriting entity);

    // Patch DTO → Entity
    @Mapping(target = "submitTest", source = "dto.submitTestId", qualifiedByName = "mapSubmitTestId")
    @Mapping(target = "testWriting", source = "dto.testWritingId", qualifiedByName = "mapTestWritingId")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment")
    void patchEntityFromDTO(SubmitTestWritingDTO dto, @MappingTarget SubmitTestWriting entity);


    @Named("mapSubmitTestId")
    default SubmitTest mapSubmitTestId(Long id) {
        if (id == null) return null;
        SubmitTest entity = new SubmitTest();
        entity.setId(id);
        return entity;
    }

    @Named("mapTestWritingId")
    default TestWriting mapTestWritingId(Long id) {
        if (id == null) return null;
        TestWriting entity = new TestWriting();
        entity.setId(id);
        return entity;
    }
}
