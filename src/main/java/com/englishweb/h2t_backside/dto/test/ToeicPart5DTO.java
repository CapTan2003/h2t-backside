package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart5DTO extends AbstractBaseDTO {

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotBlank(message = "Answer 1 cannot be blank")
    private String answer1;

    @NotBlank(message = "Answer 2 cannot be blank")
    private String answer2;

    @NotBlank(message = "Answer 3 cannot be blank")
    private String answer3;

    @NotBlank(message = "Answer 4 cannot be blank")
    private String answer4;

    @NotNull(message = "Correct answer cannot be null")
    private AnswerEnum correctAnswer;
}
