package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.TestListeningDTO;
import com.englishweb.h2t_backside.model.test.TestListening;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface TestListeningMapper {

    // Chuyển đổi từ TestListeningDTO sang TestListening Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    TestListening convertToEntity(TestListeningDTO dto);

    // Chuyển đổi từ TestListening Entity sang TestListeningDTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "audio", source = "entity.audio")
    @Mapping(target = "transcript", source = "entity.transcript")
    @Mapping(target = "questions", source = "entity.questions")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    TestListeningDTO convertToDTO(TestListening entity);

    // Cập nhật dữ liệu từ DTO vào Entity (chỉ cập nhật trường có giá trị)
    @Mapping(target = "audio", source = "dto.audio")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "questions", source = "dto.questions")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(TestListeningDTO dto, @MappingTarget TestListening entity);
}
