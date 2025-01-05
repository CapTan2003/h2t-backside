package com.englishweb.h2t_backside.model.log;

import java.time.LocalDateTime;

public class ErrorLog {
    private Long id;
    private String message;             // Lời nhắn lỗi
    private String errorCode;           // Mã lỗi
    private LocalDateTime timestamp;    // Thời gian xảy ra lỗi
}