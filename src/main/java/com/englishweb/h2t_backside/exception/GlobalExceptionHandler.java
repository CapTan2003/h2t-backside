package com.englishweb.h2t_backside.exception;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ErrorDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.DiscordNotifier;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final DiscordNotifier discordNotifier;

    // Loi du lieu DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        String errorMessage = "Validation failed for fields: " + errors.toString();

        log.error("MethodArgumentNotValidException: Validation errors - {}", errors, ex);
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("Validation Failed")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(errorMessage)
                .instance(request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.ARGUMENT_DTO_INVALID)
                .data(ex.getBindingResult().getTarget())
                .build();

        // Gửi thông báo lỗi đến Discord
        discordNotifier.buildErrorAndSend(errorDTO);
        return new ResponseEntity<>(new ResponseDTO<>(ResponseStatusEnum.FAIL, errors, "Value of fields in dto does not valid"), HttpStatus.BAD_REQUEST);
    }

    // Loi khong tim thay
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO<String>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("Resource not found: {}", ex.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("Resource Not Found")
                .status(HttpStatus.NOT_FOUND.value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.RESOURCE_NOT_FOUND)
                .data(ex.getResourceId())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .data(ex.getMessage())
                .message("Resource not found")
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Loi khoi tao tai nguyen
    @ExceptionHandler(CreateResourceException.class)
    public ResponseEntity<ResponseDTO<String>> handleCreateResourceException(CreateResourceException ex, HttpServletRequest request) {
        log.warn("Failed to create resource: {}", ex.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("Failed to Create Resource")
                .status(ex.getStatus().value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ex.getErrorCode())
                .data(ex.getData())
                .build();

        // Gửi thông báo lỗi đến Discord
        discordNotifier.buildErrorAndSend(errorDTO);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message("Failed to create resource")
                .data(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, ex.getStatus());
    }

    // Loi cap nhat tai nguyen
    @ExceptionHandler(UpdateResourceException.class)
    public ResponseEntity<ResponseDTO<String>> handleUpdateResourceException(UpdateResourceException ex, HttpServletRequest request) {
        log.warn("Failed to update resource: {}", ex.getMessage());

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message("Failed to Update Resource")
                .status(ex.getStatus().value())
                .detail(ex.getMessage())
                .instance(request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ex.getErrorCode())
                .data(ex.getData())
                .build();

        // Gửi thông báo lỗi đến Discord
        discordNotifier.buildErrorAndSend(errorDTO);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message("Failed to update resource")
                .data(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, ex.getStatus());
    }
}
