package com.englishweb.h2t_backside.exception;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ErrorDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
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
                .instance(request.getMethod() + " " + request.getRequestURI())
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
        String errorMessage = "Resource Not Found";

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.NOT_FOUND.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.RESOURCE_NOT_FOUND)
                .data(ex.getResourceId())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .data(ex.getMessage())
                .message(errorMessage)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Loi khoi tao tai nguyen
    @ExceptionHandler(CreateResourceException.class)
    public ResponseEntity<ResponseDTO<String>> handleCreateResourceException(CreateResourceException ex, HttpServletRequest request) {
        return handleResourceException(ex.getStatus(), ex.getMessage(), ex.getErrorCode(), ex.getData(), request, "Failed to Create Resource");
    }

    // Loi cap nhat tai nguyen
    @ExceptionHandler(UpdateResourceException.class)
    public ResponseEntity<ResponseDTO<String>> handleUpdateResourceException(UpdateResourceException ex, HttpServletRequest request) {
        return handleResourceException(ex.getStatus(), ex.getMessage(), ex.getErrorCode(), ex.getData(), request, "Failed to Update Resource");
    }


    // Loi tham so khong hop le
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ResponseDTO<String>> handleInvalidArgumentException(InvalidArgumentException ex, HttpServletRequest request) {
        log.warn("Invalid argument: {}", ex.getMessage());
        String errorMessage = "Invalid Argument";

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ex.getErrorCode())
                .data(ex.getData())
                .build();

        // Gửi thông báo lỗi đến Discord
        discordNotifier.buildErrorAndSend(errorDTO);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ResponseDTO<String>> handleResourceException(HttpStatus status, String message, String errorCode, Object data, HttpServletRequest request, String errorMessage) {
        log.warn("{}: {}", errorMessage, message);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(status.value())
                .detail(message)
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(errorCode)
                .data(data)
                .build();

        // Gửi thông báo lỗi đến Discord
        discordNotifier.buildErrorAndSend(errorDTO);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(message)
                .build();

        return new ResponseEntity<>(response, status);
    }

    // Xử lý lỗi sai dữ liệu đầu vào kiểu enum
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseDTO<String>> handleEnumTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.warn("Method argument type mismatch: {}", ex.getMessage());
        String errorMessage = "Method argument type mismatch";

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.ARGUMENT_TYPE_MISMATCH)
                .data(ex.getName())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Xử lý lỗi sai dữ liệu đầu vào
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("HttpMessageNotReadableException: {}", ex.getMessage());
        String errorMessage = "Invalid input data format";

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.ARGUMENT_TYPE_MISMATCH)
                .data(ex.getMessage())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Xử lý lỗi thieu tham so
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseDTO<String>> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        log.warn("Missing Servlet Request Parameter Exception: {}", ex.getMessage());
        String errorMessage = "Missing Request Parameter";

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.MISSING_REQUEST_PARAMETER)
                .data(ex.getParameterName())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseDTO<String>> handleIOException(IOException ex, HttpServletRequest request) {
        log.error("IOException: {}", ex.getMessage());

        String errorMessage = "IO Exception";
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.IO_EXCEPTION)
                .data(ex.getMessage())
                .build();

        discordNotifier.buildErrorAndSend(errorDTO);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> handleException(Exception ex, HttpServletRequest request) {
        log.error("Exception: {}", ex.getMessage());
        String errorMessage = "Internal Server Error";
        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(ex.getMessage())
                .instance(request.getMethod() + " " + request.getRequestURI())
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .errorCode(ErrorApiCodeContent.UNEXPECTED_ERROR)
                .data(ex.getMessage())
                .build();
        discordNotifier.buildErrorAndSend(errorDTO);
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.FAIL)
                .message(errorMessage)
                .data(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
