package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicDTO extends AbstractBaseDTO {

    @NotNull(message = "Title cannot be null")
    private String title;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @NotNull(message = "Questions Part 1 cannot be null")
    private String questionsPart1;

    @NotNull(message = "Questions Part 2 cannot be null")
    private String questionsPart2;

    @NotNull(message = "Questions Part 3 & 4 cannot be null")
    private String questionsPart3_4;

    @NotNull(message = "Questions Part 5 cannot be null")
    private String questionsPart5;

    @NotNull(message = "Questions Part 6 cannot be null")
    private String questionsPart6;

    @NotNull(message = "Questions Part 7 cannot be null")
    private String questionsPart7;
}
