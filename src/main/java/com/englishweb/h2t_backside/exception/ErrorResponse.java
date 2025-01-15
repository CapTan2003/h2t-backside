package com.englishweb.h2t_backside.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse<E> {
    private LocalDateTime timestamp;  // Thời gian xảy ra lỗi
    private Object data;              // Dữ liệu đầu vào dẫn đến lỗi
    private String errorCode;         // Mã lỗi
}
