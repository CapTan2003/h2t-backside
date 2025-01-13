package com.englishweb.h2t_backside.service.impl;

import com.englishweb.h2t_backside.dto.interfacedto.BaseDTO;
import com.englishweb.h2t_backside.model.interfacemodel.BaseEntity;
import com.englishweb.h2t_backside.service.BaseService;
import com.englishweb.h2t_backside.service.DiscordNotifier;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    protected ObjectMapper objectMapper;

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
        log.info("Creating new entity with DTO");
        try {
            Entity entity = convertToEntity(dto);
            entity.setId(null);
            DTO savedDTO = convertToDTO(repository.save(entity));
            log.info("Created entity with ID: {}", savedDTO.getId());
            return savedDTO;
        } catch (Exception e) {
            this.createError(dto, e);
            return null;
        }
    }

    @Override
    public DTO update(DTO dto, Long id) {
        log.info("Updating entity with DTO");
        try{
            Entity entity = convertToEntity(dto);
            entity.setId(id);
            DTO savedDTO = convertToDTO(repository.save(entity));
            log.info("Updated entity with ID: {}", savedDTO.getId());
            return savedDTO;
        } catch (Exception e) {
            this.updateError(dto, id, e);
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting entity with ID: {}", id);
        Optional<Entity> entityOptional = repository.findById(id);
        if (entityOptional.isEmpty()) {
            this.findByIdError(id);
            return;
        }
        repository.deleteById(id);
        log.info("Deleted entity with ID: {}", id);
    }

    @Override
    public boolean isExist(Long id) {
        Optional<Entity> entityOptional = repository.findById(id);
        return entityOptional.isPresent();
    }

    protected abstract Entity convertToEntity(DTO dto);

    protected abstract DTO convertToDTO(Entity entity);

    protected abstract void findByIdError(Long id);

    protected abstract void createError(DTO dto, Exception e);

    protected abstract void updateError(DTO dto, Long id, Exception e);
}
