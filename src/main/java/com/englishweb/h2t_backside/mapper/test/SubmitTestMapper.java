package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitTestDTO;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.model.test.Test;
import com.englishweb.h2t_backside.model.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface SubmitTestMapper {

    // DTO → Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "user", source = "dto.userId", qualifiedByName = "mapUserId")
    @Mapping(target = "test", source = "dto.testId", qualifiedByName = "mapTestId")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment", defaultValue = "")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitTest convertToEntity(SubmitTestDTO dto);

    // Entity → DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "userId", source = "entity.user.id")
    @Mapping(target = "testId", source = "entity.test.id")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "comment", source = "entity.comment")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitTestDTO convertToDTO(SubmitTest entity);

    // Patch DTO → Entity
    @Mapping(target = "user", source = "dto.userId", qualifiedByName = "mapUserId")
    @Mapping(target = "test", source = "dto.testId", qualifiedByName = "mapTestId")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitTestDTO dto, @MappingTarget SubmitTest entity);


    @Named("mapUserId")
    default User mapUserId(Long id) {
        if (id == null) return null;
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapTestId")
    default Test mapTestId(Long id) {
        if (id == null) return null;
        Test test = new Test();
        test.setId(id);
        return test;
    }
}
