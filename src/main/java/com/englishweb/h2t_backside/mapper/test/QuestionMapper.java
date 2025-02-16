package com.englishweb.h2t_backside.mapper.test;
import com.englishweb.h2t_backside.dto.test.QuestionDTO;
import com.englishweb.h2t_backside.model.test.Question;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = AnswerMapper.class,
        builder = @Builder(disableBuilder = true)
)
public interface QuestionMapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "answers", source = "dto.answers")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    Question convertToEntity(QuestionDTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "content", source = "entity.content")
    @Mapping(target = "answers", source = "entity.answers")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    QuestionDTO convertToDTO(Question entity);

    // Cập nhật dữ liệu từ DTO vào Entity (bỏ qua trường null)
    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "answers", source = "dto.answers")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(QuestionDTO dto, @MappingTarget Question entity);
}
