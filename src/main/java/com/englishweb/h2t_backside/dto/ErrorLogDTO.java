package com.englishweb.h2t_backside.dto;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorLogDTO extends AbstractBaseDTO {
    private String message;
    private String errorCode;
    private SeverityEnum severity;
}
