package com.englishweb.h2t_backside.dto.homepage;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class HeroInfoDTO {
    private long students;
    private long teachers;
    private long lessons;
    private long tests;
}
