package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitTestWritingDTO;
import com.englishweb.h2t_backside.mapper.test.SubmitTestMapper;
import com.englishweb.h2t_backside.mapper.test.TestWritingMapper;
import com.englishweb.h2t_backside.model.test.SubmitTestWriting;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {SubmitTestMapper.class, TestWritingMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface SubmitTestWritingMapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitTest", source = "dto.submitTest")
    @Mapping(target = "writing", source = "dto.writing")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "score", source = "dto.score")
    SubmitTestWriting convertToEntity(SubmitTestWritingDTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitTest", source = "entity.submitTest")
    @Mapping(target = "writing", source = "entity.writing")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitTestWritingDTO convertToDTO(SubmitTestWriting entity);

    // Cập nhật dữ liệu từ DTO vào Entity (bỏ qua trường null)
    @Mapping(target = "submitTest", source = "dto.submitTest")
    @Mapping(target = "writing", source = "dto.writing")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "score", source = "dto.score")
    void patchEntityFromDTO(SubmitTestWritingDTO dto, @MappingTarget SubmitTestWriting entity);
}
