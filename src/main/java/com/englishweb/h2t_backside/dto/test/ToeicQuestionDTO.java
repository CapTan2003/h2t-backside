package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicQuestionDTO extends AbstractBaseDTO {

    @NotBlank(message = "Question content cannot be blank")
    private String content;

    @NotBlank(message = "Explanation cannot be blank")
    private String explanation;

    @NotNull(message = "Answers list cannot be null")
    private List<ToeicAnswerDTO> toeicAnswers;
}
