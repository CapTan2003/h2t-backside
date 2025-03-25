package com.englishweb.h2t_backside.mapper.test;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionAnswerDTO;
import com.englishweb.h2t_backside.model.test.SubmitCompetitionAnswer;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {SubmitCompetitionMapper.class, QuestionMapper.class, AnswerMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface SubmitCompetitionAnswerMapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "submitCompetition", source = "dto.submitCompetition")
    @Mapping(target = "question", source = "dto.question")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status", defaultValue = "true")
    SubmitCompetitionAnswer convertToEntity(SubmitCompetitionAnswerDTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "submitCompetition", source = "entity.submitCompetition")
    @Mapping(target = "question", source = "entity.question")
    @Mapping(target = "answer", source = "entity.answer")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitCompetitionAnswerDTO convertToDTO(SubmitCompetitionAnswer entity);

    // Cập nhật dữ liệu từ DTO vào Entity (bỏ qua trường null)
    @Mapping(target = "submitCompetition", source = "dto.submitCompetition")
    @Mapping(target = "question", source = "dto.question")
    @Mapping(target = "answer", source = "dto.answer")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitCompetitionAnswerDTO dto, @MappingTarget SubmitCompetitionAnswer entity);
}
