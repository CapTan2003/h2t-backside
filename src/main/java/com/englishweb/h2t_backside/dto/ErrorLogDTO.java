package com.englishweb.h2t_backside.dto;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLogDTO {
    private Long id;
    private String message;
    private String errorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime timestamp;
    private SeverityEnum severity;
    private boolean fixed;
}
