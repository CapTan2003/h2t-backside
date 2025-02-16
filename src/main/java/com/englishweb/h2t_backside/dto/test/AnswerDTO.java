package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO extends AbstractBaseDTO {

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotNull(message = "Correct flag cannot be null")
    private Boolean correct;

    private Long questionId;
}
