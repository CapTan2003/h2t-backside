package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitTestSpeakingDTO;
import com.englishweb.h2t_backside.mapper.test.QuestionMapper;
import com.englishweb.h2t_backside.mapper.test.SubmitTestMapper;
import com.englishweb.h2t_backside.model.test.SubmitTestSpeaking;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {SubmitTestMapper.class, QuestionMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface SubmitTestSpeakingMapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitTest", source = "dto.submitTest")
    @Mapping(target = "question", source = "dto.question")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "score", source = "dto.score")
    SubmitTestSpeaking convertToEntity(SubmitTestSpeakingDTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitTest", source = "entity.submitTest")
    @Mapping(target = "question", source = "entity.question")
    @Mapping(target = "transcript", source = "entity.transcript")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitTestSpeakingDTO convertToDTO(SubmitTestSpeaking entity);

    // Cập nhật dữ liệu từ DTO vào Entity (bỏ qua trường null)
    @Mapping(target = "submitTest", source = "dto.submitTest")
    @Mapping(target = "question", source = "dto.question")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "score", source = "dto.score")
    void patchEntityFromDTO(SubmitTestSpeakingDTO dto, @MappingTarget SubmitTestSpeaking entity);
}
