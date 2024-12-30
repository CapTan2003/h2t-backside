package com.englishweb.h2t_backside.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;  // Thời gian xảy ra lỗi
    private Object data;              // Dữ liệu đầu vào dẫn đến lỗi
    private String errorCode;         // Mã lỗi
}
