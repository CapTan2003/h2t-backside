package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionWritingDTO;
import com.englishweb.h2t_backside.mapper.test.SubmitCompetitionMapper;
import com.englishweb.h2t_backside.mapper.test.TestWritingMapper;
import com.englishweb.h2t_backside.model.test.SubmitCompetitionWriting;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {SubmitCompetitionMapper.class, TestWritingMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface SubmitCompetitionWritingMapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitCompetition", source = "dto.submitCompetition")
    @Mapping(target = "writing", source = "dto.writing")
    @Mapping(target = "content", source = "dto.content", defaultValue = "")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    SubmitCompetitionWriting convertToEntity(SubmitCompetitionWritingDTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitCompetition", source = "entity.submitCompetition")
    @Mapping(target = "writing", source = "entity.writing")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitCompetitionWritingDTO convertToDTO(SubmitCompetitionWriting entity);

    // Cập nhật dữ liệu từ DTO vào Entity (bỏ qua trường null)
    @Mapping(target = "submitCompetition", source = "dto.submitCompetition")
    @Mapping(target = "writing", source = "dto.writing")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitCompetitionWritingDTO dto, @MappingTarget SubmitCompetitionWriting entity);
}
