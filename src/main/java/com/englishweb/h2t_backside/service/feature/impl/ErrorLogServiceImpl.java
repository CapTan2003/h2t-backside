package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.ErrorLogDTO;
import com.englishweb.h2t_backside.dto.filter.ErrorLogFilterDTO;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.mapper.ErrorLogMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.log.ErrorLog;
import com.englishweb.h2t_backside.repository.ErrorLogRepository;
import com.englishweb.h2t_backside.service.feature.ErrorLogService;
import com.englishweb.h2t_backside.utils.ErrorLogPagination;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ErrorLogServiceImpl implements ErrorLogService {
    ErrorLogRepository repository;
    private final ErrorLogMapper mapper;

    @Override
    public ErrorLogDTO create(ErrorLogDTO dto) {
        try {
            log.info("Creating error log");
            ErrorLog entity = convertToEntity(dto);
            entity.setId(null);
            ErrorLog created = repository.save(entity);
            log.info("Created error log successfully");
            return convertToDTO(created);
        } catch (Exception e) {
            log.error("Error when creating error log: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public ErrorLogDTO update(Long id, ErrorLogDTO dto) {
        log.info("Updating error log entity with PATCH-style update");

        try {
            ErrorLog existingEntity = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id, "ErrorLog not found", SeverityEnum.MEDIUM));

            patchEntityFromDTO(dto, existingEntity);

            ErrorLog savedEntity = repository.save(existingEntity);
            ErrorLogDTO savedDTO = convertToDTO(savedEntity);
            log.info("Updated error log with ID: {}", savedDTO.getId());

            return savedDTO;
        } catch (Exception e) {
            log.error("Error updating error log with ID {}: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public Page<ErrorLogDTO> findByPage(int page, int size, String sortFields, ErrorLogFilterDTO filter) {
        Page<ErrorLog> errorLogs = ErrorLogPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, ErrorLog.class
        );
        return errorLogs.map(this::convertToDTO);
    }

    protected void patchEntityFromDTO(ErrorLogDTO dto, ErrorLog entity) { mapper.patchEntityFromDTO(dto, entity); }

    protected ErrorLog convertToEntity(ErrorLogDTO dto) { return mapper.convertToEntity(dto); }

    protected ErrorLogDTO convertToDTO(ErrorLog entity) { return mapper.convertToDTO(entity); }

}
