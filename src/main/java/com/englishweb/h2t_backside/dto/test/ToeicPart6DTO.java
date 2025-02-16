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
public class ToeicPart6DTO extends AbstractBaseDTO {

    @NotBlank(message = "File cannot be blank")
    private String file; // Lưu đường dẫn file docx

    @NotBlank(message = "Content question 1 cannot be blank")
    private String contentQuestion1;

    @NotBlank(message = "Content question 2 cannot be blank")
    private String contentQuestion2;

    @NotBlank(message = "Content question 3 cannot be blank")
    private String contentQuestion3;

    @NotBlank(message = "Content question 4 cannot be blank")
    private String contentQuestion4;

    @NotBlank private String answer1Q1;
    @NotBlank private String answer2Q1;
    @NotBlank private String answer3Q1;
    @NotBlank private String answer4Q1;

    @NotBlank private String answer1Q2;
    @NotBlank private String answer2Q2;
    @NotBlank private String answer3Q2;
    @NotBlank private String answer4Q2;

    @NotBlank private String answer1Q3;
    @NotBlank private String answer2Q3;
    @NotBlank private String answer3Q3;
    @NotBlank private String answer4Q3;

    @NotBlank private String answer1Q4;
    @NotBlank private String answer2Q4;
    @NotBlank private String answer3Q4;
    @NotBlank private String answer4Q4;

    @NotNull(message = "Correct answer 1 cannot be null")
    private AnswerEnum correctAnswer1;

    @NotNull(message = "Correct answer 2 cannot be null")
    private AnswerEnum correctAnswer2;

    @NotNull(message = "Correct answer 3 cannot be null")
    private AnswerEnum correctAnswer3;

    @NotNull(message = "Correct answer 4 cannot be null")
    private AnswerEnum correctAnswer4;
}
