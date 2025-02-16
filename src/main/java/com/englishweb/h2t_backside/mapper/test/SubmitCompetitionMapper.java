package com.englishweb.h2t_backside.mapper.test;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionDTO;
import com.englishweb.h2t_backside.mapper.UserMapper;
import com.englishweb.h2t_backside.model.test.SubmitCompetition;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {UserMapper.class, CompetitionTestMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface SubmitCompetitionMapper {

    // Chuyển đổi từ DTO sang Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "user", source = "dto.user")
    @Mapping(target = "test", source = "dto.test")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    SubmitCompetition convertToEntity(SubmitCompetitionDTO dto);

    // Chuyển đổi từ Entity sang DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "score", source = "entity.score")
    @Mapping(target = "user", source = "entity.user")
    @Mapping(target = "test", source = "entity.test")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    SubmitCompetitionDTO convertToDTO(SubmitCompetition entity);

    // Cập nhật dữ liệu từ DTO vào Entity (bỏ qua trường null)
    @Mapping(target = "score", source = "dto.score")
    @Mapping(target = "user", source = "dto.user")
    @Mapping(target = "test", source = "dto.test")
    @Mapping(target = "status", source = "dto.status")
    void patchEntityFromDTO(SubmitCompetitionDTO dto, @MappingTarget SubmitCompetition entity);
}
