package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToeicPart6DTO extends AbstractBaseDTO {

    @NotNull(message = "Submit TOEIC cannot be null")
    private SubmitToeicDTO submitToeic;

    @NotNull(message = "Toeic Part 6 cannot be null")
    private ToeicPart6DTO toeicPart6;

    @NotNull(message = "Answer for question 1 cannot be null")
    private AnswerEnum answerQ1;

    @NotNull(message = "Answer for question 2 cannot be null")
    private AnswerEnum answerQ2;

    @NotNull(message = "Answer for question 3 cannot be null")
    private AnswerEnum answerQ3;

    @NotNull(message = "Answer for question 4 cannot be null")
    private AnswerEnum answerQ4;
}
