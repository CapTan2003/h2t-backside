package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.ErrorLogDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.ErrorLogFilterDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.feature.ErrorLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/error-logs")
@RequiredArgsConstructor
@Slf4j
public class ErrorLogController {

    private final ErrorLogService errorLogService;

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ErrorLogDTO> update(@PathVariable Long id, @Valid @RequestBody ErrorLogDTO errorLogDTO) {
        ErrorLogDTO updatedErrorLog = errorLogService.update(id, errorLogDTO);

        return ResponseDTO.<ErrorLogDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedErrorLog)
                .message("Error log updated successfully")
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Page<ErrorLogDTO>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @ModelAttribute ErrorLogFilterDTO filter) {

        Page<ErrorLogDTO> errorLogs = errorLogService.findByPage(page, size, sortFields, filter);

        return ResponseDTO.<Page<ErrorLogDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(errorLogs)
                .message("Error logs retrieved successfully with filters")
                .build();
    }

}

