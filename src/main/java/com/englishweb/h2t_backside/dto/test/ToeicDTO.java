package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    private List<Long> questionsPart1;

    @NotNull(message = "Questions Part 2 cannot be null")
    private List<Long> questionsPart2;

    @NotNull(message = "Questions Part 3 cannot be null")
    private List<Long> questionsPart3;

    @NotNull(message = "Questions Part 4 cannot be null")
    private List<Long> questionsPart4;

    @NotNull(message = "Questions Part 5 cannot be null")
    private List<Long> questionsPart5;

    @NotNull(message = "Questions Part 6 cannot be null")
    private List<Long> questionsPart6;

    @NotNull(message = "Questions Part 7 cannot be null")
    private List<Long> questionsPart7;

    private Double ScoreLastOfTest;
}
