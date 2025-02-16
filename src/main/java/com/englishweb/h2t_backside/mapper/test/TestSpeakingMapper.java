package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.TestSpeakingDTO;
import com.englishweb.h2t_backside.model.test.TestSpeaking;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TestSpeakingMapper {

    // Chuyển đổi từ TestSpeakingDTO sang TestSpeaking Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    TestSpeaking convertToEntity(TestSpeakingDTO dto);

    // Chuyển đổi từ TestSpeaking Entity sang TestSpeakingDTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "questions", source = "entity.questions")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    TestSpeakingDTO convertToDTO(TestSpeaking entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(TestSpeakingDTO dto, @MappingTarget TestSpeaking entity);
}
