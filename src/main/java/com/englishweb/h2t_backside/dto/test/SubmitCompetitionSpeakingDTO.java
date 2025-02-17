package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitCompetitionSpeakingDTO extends AbstractBaseDTO {

    @NotNull(message = "Submit competition cannot be null")
    private SubmitCompetitionDTO submitCompetition;

    @NotNull(message = "Question cannot be null")
    private QuestionDTO question;

    @NotBlank(message = "Transcript cannot be empty")
    private String transcript;

    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be at least 0")
    private Integer score;
}
