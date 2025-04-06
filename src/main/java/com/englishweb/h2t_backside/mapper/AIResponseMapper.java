package com.englishweb.h2t_backside.mapper;

import com.englishweb.h2t_backside.dto.AIResponseDTO;
import com.englishweb.h2t_backside.model.features.AIResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AIResponseMapper {

    @Mapping(target = "user.id", source = "userId")
    AIResponse convertToEntity(AIResponseDTO dto);

    @Mapping(target = "userId", source = "user.id")
    AIResponseDTO convertToDTO(AIResponse entity);

    @Mapping(target = "request", source = "dto.request")
    @Mapping(target = "response", source = "dto.response")
    @Mapping(target = "evaluate", source = "dto.evaluate")
    void patchEntityFromDTO(AIResponseDTO dto, @MappingTarget AIResponse entity);
}

