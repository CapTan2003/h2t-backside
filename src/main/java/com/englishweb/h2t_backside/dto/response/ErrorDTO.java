package com.englishweb.h2t_backside.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
    private Object data;
}
