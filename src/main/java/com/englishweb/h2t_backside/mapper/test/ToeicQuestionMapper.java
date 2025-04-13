package com.englishweb.h2t_backside.mapper.test;

import com.englishweb.h2t_backside.dto.test.ToeicQuestionDTO;
import com.englishweb.h2t_backside.model.test.ToeicQuestion;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ToeicQuestionMapper {

    ToeicQuestion convertToEntity(ToeicQuestionDTO dto);

    ToeicQuestionDTO convertToDTO(ToeicQuestion entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntityFromDTO(ToeicQuestionDTO dto, @MappingTarget ToeicQuestion entity);
}
