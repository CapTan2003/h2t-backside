package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.ErrorLogDTO;
import com.englishweb.h2t_backside.model.log.ErrorLog;
import com.englishweb.h2t_backside.repository.ErrorLogRepository;
import com.englishweb.h2t_backside.service.feature.ErrorLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ErrorLogServiceImpl implements ErrorLogService {
    ErrorLogRepository repository;

    @Override
    public ErrorLogDTO create(ErrorLogDTO dto) {
        try {
            log.info("Creating error log");
            ErrorLog entity = convertToEntity(dto);
            entity.setId(null);
            ErrorLog created  = repository.save(entity);
            log.info("Created error log successfully");
            return convertToDTO(created);
        } catch (Exception e){
            log.error("Error when creating error log: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Page<ErrorLogDTO> findByPage(int page, int size) {
        return null;
    }

    private ErrorLogDTO convertToDTO(ErrorLog entity){
        return ErrorLogDTO.builder()
                .id(entity.getId())
                .message(entity.getMessage())
                .errorCode(entity.getErrorCode())
                .timestamp(entity.getTimestamp())
                .build();
    }

    private ErrorLog convertToEntity(ErrorLogDTO dto){
        return ErrorLog.builder()
                .id(dto.getId())
                .message(dto.getMessage())
                .errorCode(dto.getErrorCode())
                .timestamp(dto.getTimestamp())
                .build();
    }
}
