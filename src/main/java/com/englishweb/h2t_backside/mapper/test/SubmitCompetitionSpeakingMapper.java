package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionSpeakingDTO;
import com.englishweb.h2t_backside.mapper.test.QuestionMapper;
import com.englishweb.h2t_backside.mapper.test.SubmitCompetitionMapper;
import com.englishweb.h2t_backside.model.test.SubmitCompetitionSpeaking;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {SubmitCompetitionMapper.class, QuestionMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface SubmitCompetitionSpeakingMapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitCompetition", source = "dto.submitCompetition")
    @Mapping(target = "question", source = "dto.question")
    @Mapping(target = "transcript", source = "dto.transcript", defaultValue = "")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    SubmitCompetitionSpeaking convertToEntity(SubmitCompetitionSpeakingDTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitCompetition", source = "entity.submitCompetition")
    @Mapping(target = "question", source = "entity.question")
    @Mapping(target = "transcript", source = "entity.transcript")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitCompetitionSpeakingDTO convertToDTO(SubmitCompetitionSpeaking entity);

    // Cập nhật dữ liệu từ DTO vào Entity (bỏ qua trường null)
    @Mapping(target = "submitCompetition", source = "dto.submitCompetition")
    @Mapping(target = "question", source = "dto.question")
    @Mapping(target = "transcript", source = "dto.transcript")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitCompetitionSpeakingDTO dto, @MappingTarget SubmitCompetitionSpeaking entity);
}
