package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicAnswerDTO;
import com.englishweb.h2t_backside.model.test.ToeicAnswer;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ToeicAnswerMapper {

    ToeicAnswer convertToEntity(ToeicAnswerDTO dto);

    ToeicAnswerDTO convertToDTO(ToeicAnswer entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntityFromDTO(ToeicAnswerDTO dto, @MappingTarget ToeicAnswer entity);
}
