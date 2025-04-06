package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.AIResponseDTO;
import com.englishweb.h2t_backside.mapper.AIResponseMapper;
import com.englishweb.h2t_backside.model.features.AIResponse;
import com.englishweb.h2t_backside.repository.AIResponseRepository;
import com.englishweb.h2t_backside.service.feature.AIResponseService;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AIResponseServiceImpl extends BaseServiceImpl<AIResponseDTO, AIResponse, AIResponseRepository> implements AIResponseService {
    private final AIResponseMapper mapper;

    public AIResponseServiceImpl(AIResponseRepository repository, DiscordNotifier discordNotifier, AIResponseMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {

    }

    @Override
    protected void createError(AIResponseDTO dto, Exception ex) {

    }

    @Override
    protected void updateError(AIResponseDTO dto, Long id, Exception ex) {

    }

    @Override
    protected void patchEntityFromDTO(AIResponseDTO dto, AIResponse entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected AIResponse convertToEntity(AIResponseDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected AIResponseDTO convertToDTO(AIResponse entity) {
        return mapper.convertToDTO(entity);
    }

}
