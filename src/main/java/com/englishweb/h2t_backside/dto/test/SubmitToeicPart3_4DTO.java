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
public class SubmitToeicPart3_4DTO extends AbstractBaseDTO {

    @NotNull(message = "Submit TOEIC id cannot be null")
    private Long submitToeicId;

    @NotNull(message = "Toeic Part 3 and 4 id id cannot be null")
    private Long toeicPart3_4Id;

    @NotNull(message = "Answer for question 1 cannot be null")
    private AnswerEnum answerQ1;

    @NotNull(message = "Answer for question 2 cannot be null")
    private AnswerEnum answerQ2;

    @NotNull(message = "Answer for question 3 cannot be null")
    private AnswerEnum answerQ3;
}
