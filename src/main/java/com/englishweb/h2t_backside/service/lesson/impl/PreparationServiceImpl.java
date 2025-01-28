package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.PreparationDTO;
import com.englishweb.h2t_backside.mapper.lesson.PreparationMapper;
import com.englishweb.h2t_backside.model.lesson.Preparation;
import com.englishweb.h2t_backside.repository.lesson.PreparationRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.PreparationService;

public class PreparationServiceImpl extends BaseServiceImpl<PreparationDTO, Preparation, PreparationRepository> implements PreparationService {

    private final PreparationMapper mapper;

    public PreparationServiceImpl(PreparationRepository repository, DiscordNotifier discordNotifier, PreparationMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {

    }

    @Override
    protected void createError(PreparationDTO dto, Exception ex) {

    }

    @Override
    protected void updateError(PreparationDTO dto, Long id, Exception ex) {

    }

    @Override
    protected void patchEntityFromDTO(PreparationDTO dto, Preparation entity) {

    }

    @Override
    protected Preparation convertToEntity(PreparationDTO dto) {
        return null;
    }

    @Override
    protected PreparationDTO convertToDTO(Preparation entity) {
        return null;
    }
}
