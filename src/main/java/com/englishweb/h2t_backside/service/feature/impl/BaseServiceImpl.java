package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.interfacedto.BaseDTO;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.model.interfacemodel.BaseEntity;
import com.englishweb.h2t_backside.service.feature.BaseService;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
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
    public DTO patch(DTO dto, Long id) {
        log.info("Updating with patch entity with DTO");
        try{
            Entity existingEntity = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id, "Resource not found"));

            patchEntityFromDTO(dto, existingEntity);
            Entity savedEntity = repository.save(existingEntity);
            DTO savedDTO = convertToDTO(savedEntity);
            log.info("Updated with patch entity with ID: {}", savedDTO.getId());
            return savedDTO;
        } catch (Exception e) {
            this.updateError(dto, id, e);
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting entity with ID: {}", id);
        Optional<Entity> entityOptional = repository.findById(id);
        if (entityOptional.isEmpty()) {
            this.findByIdError(id);
            return false;
        }
        repository.deleteById(id);
        log.info("Deleted entity with ID: {}", id);
        return true;
    }

    @Override
    public boolean isExist(Long id) {
        Optional<Entity> entityOptional = repository.findById(id);
        return entityOptional.isPresent();
    }

    @Override
    public void deleteAll(List<Long> ids) {
        log.info("Deleting all entities with IDs: {}", ids);
        repository.deleteAllById(ids);
        log.info("Deleted all entities with IDs: {}", ids);
    }

    protected abstract void findByIdError(Long id);

    protected abstract void createError(DTO dto, Exception ex);

    protected abstract void updateError(DTO dto, Long id, Exception ex);

    protected abstract void patchEntityFromDTO(DTO dto, Entity entity);

    protected abstract Entity convertToEntity(DTO dto);

    protected abstract DTO convertToDTO(Entity entity);

}
