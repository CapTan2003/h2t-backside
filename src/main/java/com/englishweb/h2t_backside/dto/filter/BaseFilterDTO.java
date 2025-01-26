package com.englishweb.h2t_backside.dto.filter;

import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseFilterDTO {
    private LocalDateTime startCreatedAt;
    private LocalDateTime endCreatedAt;
    private LocalDateTime startUpdatedAt;
    private LocalDateTime endUpdatedAt;
    private StatusEnum status;
}
