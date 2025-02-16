package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitTestDTO;
import com.englishweb.h2t_backside.mapper.UserMapper;
import com.englishweb.h2t_backside.mapper.test.TestMapper;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserMapper.class, TestMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface SubmitTestMapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "user", source = "dto.user")
    @Mapping(target = "test", source = "dto.test")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment", defaultValue = "")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    SubmitTest convertToEntity(SubmitTestDTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "user", source = "entity.user")
    @Mapping(target = "test", source = "entity.test")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "comment", source = "entity.comment")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitTestDTO convertToDTO(SubmitTest entity);

    // Cập nhật dữ liệu từ DTO vào Entity (bỏ qua trường null)
    @Mapping(target = "user", source = "dto.user")
    @Mapping(target = "test", source = "dto.test")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "comment", source = "dto.comment")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitTestDTO dto, @MappingTarget SubmitTest entity);
}
