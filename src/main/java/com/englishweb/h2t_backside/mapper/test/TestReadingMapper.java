package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.TestReadingDTO;
import com.englishweb.h2t_backside.model.test.TestReading;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TestReadingMapper {

    // Chuyển đổi từ TestReadingDTO sang TestReading Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    TestReading convertToEntity(TestReadingDTO dto);

    // Chuyển đổi từ TestReading Entity sang TestReadingDTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "file", source = "entity.file")
    @Mapping(target = "questions", source = "entity.questions")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    TestReadingDTO convertToDTO(TestReading entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "file", source = "dto.file")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(TestReadingDTO dto, @MappingTarget TestReading entity);
}
