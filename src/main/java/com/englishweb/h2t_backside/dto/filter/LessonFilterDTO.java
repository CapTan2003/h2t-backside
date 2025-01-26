package com.englishweb.h2t_backside.dto.filter;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonFilterDTO extends BaseFilterDTO {
    private String title;
}
