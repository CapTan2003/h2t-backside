package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart3_4DTO extends AbstractBaseDTO {

    @NotBlank(message = "Audio URL cannot be blank")
    private String audio;

    private String image;

    @NotBlank(message = "Content for question 1 cannot be blank")
    private String contentQuestion1;

    @NotBlank(message = "Content for question 2 cannot be blank")
    private String contentQuestion2;

    @NotBlank(message = "Content for question 3 cannot be blank")
    private String contentQuestion3;

    @NotBlank(message = "Answer 1 for question 1 cannot be blank")
    private String answer1Q1;
    private String answer2Q1;
    private String answer3Q1;
    private String answer4Q1;

    @NotBlank(message = "Answer 1 for question 2 cannot be blank")
    private String answer1Q2;
    private String answer2Q2;
    private String answer3Q2;
    private String answer4Q2;

    @NotBlank(message = "Answer 1 for question 3 cannot be blank")
    private String answer1Q3;
    private String answer2Q3;
    private String answer3Q3;
    private String answer4Q3;

    @NotNull(message = "Correct answer for question 1 cannot be null")
    private AnswerEnum correctAnswer1;

    @NotNull(message = "Correct answer for question 2 cannot be null")
    private AnswerEnum correctAnswer2;

    @NotNull(message = "Correct answer for question 3 cannot be null")
    private AnswerEnum correctAnswer3;
}
