package com.englishweb.h2t_backside.dto.filter;

import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonFilterDTO {
    private String title;
    private LocalDateTime startCreatedAt;
    private LocalDateTime endCreatedAt;
    private LocalDateTime startUpdatedAt;
    private LocalDateTime endUpdatedAt;
    private StatusEnum status;
}
