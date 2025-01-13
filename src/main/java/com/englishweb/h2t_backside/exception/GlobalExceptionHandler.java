package com.englishweb.h2t_backside.exception;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.DiscordNotifier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final DiscordNotifier discordNotifier;

    // Loi du lieu DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        log.error("MethodArgumentNotValidException: Validation errors - {}", errors, ex);
        Object target = ex.getBindingResult().getTarget();
        discordNotifier.buildErrorAndSend(Objects.requireNonNullElse(target, errors), "Value of fields in DTO does not valid", ErrorApiCodeContent.ARGUMENT_DTO_INVALID);
        return new ResponseEntity<>(new ResponseDTO<>(ResponseStatusEnum.FAIL, errors, "Value of fields in dto does not valid"), HttpStatus.BAD_REQUEST);
    }
}
