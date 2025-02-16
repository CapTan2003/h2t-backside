package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToeicPart3_4DTO extends AbstractBaseDTO {

    @NotNull(message = "Submit TOEIC cannot be null")
    private SubmitToeicDTO submitToeic;

    @NotNull(message = "Toeic Part 3_4 cannot be null")
    private ToeicPart3_4DTO toeicPart3_4;

    @NotNull(message = "Answer for question 1 cannot be null")
    private AnswerEnum answerQ1;

    @NotNull(message = "Answer for question 2 cannot be null")
    private AnswerEnum answerQ2;

    @NotNull(message = "Answer for question 3 cannot be null")
    private AnswerEnum answerQ3;
}
