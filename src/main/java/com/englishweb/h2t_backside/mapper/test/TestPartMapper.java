package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.TestPartDTO;
import com.englishweb.h2t_backside.model.test.TestPart;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TestPartMapper {

    // Chuyển đổi từ TestPartDTO sang TestPart Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    TestPart convertToEntity(TestPartDTO dto);

    // Chuyển đổi từ TestPart Entity sang TestPartDTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "questions", source = "entity.questions")
    @Mapping(target = "type", source = "entity.type")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    TestPartDTO convertToDTO(TestPart entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(TestPartDTO dto, @MappingTarget TestPart entity);
}
