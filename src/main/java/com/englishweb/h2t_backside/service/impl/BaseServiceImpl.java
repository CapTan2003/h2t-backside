package com.englishweb.h2t_backside.service.impl;

import com.englishweb.h2t_backside.dto.interfacedto.BaseDTO;
import com.englishweb.h2t_backside.model.interfacemodel.BaseEntity;
import com.englishweb.h2t_backside.service.BaseService;
import com.englishweb.h2t_backside.service.DiscordNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public abstract class BaseServiceImpl<DTO extends BaseDTO, Entity extends BaseEntity, R extends JpaRepository<Entity, Long>>
        implements BaseService<DTO> {
    protected R repository;
    protected DiscordNotifier discordNotifier;

    @Override
    public DTO findById(Long id) {
        log.info("Finding by ID: {}", id);
        Optional<Entity> entityOptional = repository.findById(id);

        if (entityOptional.isEmpty()) {
            this.findByIdError(id);
            return null;
        }

        Entity entity = entityOptional.get();
        DTO dto = convertToDTO(entity);
        log.info("Found entity with ID: {}", id);
        return dto;
    }

    @Override
    public DTO create(DTO dto) {
        return null;
    }

    @Override
    public DTO update(DTO dto, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean isExist(Long id) {
        return false;
    }

    protected abstract Entity convertToEntity(DTO dto);

    protected abstract DTO convertToDTO(Entity entity);

    protected abstract void findByIdError(Long id);
}
