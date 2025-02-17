package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitTestAnswerDTO extends AbstractBaseDTO {

    @NotNull(message = "SubmitTest cannot be null")
    private SubmitTestDTO submitTest;

    @NotNull(message = "Question cannot be null")
    private QuestionDTO question;

    @NotNull(message = "Answer cannot be null")
    private AnswerDTO answer;
}
