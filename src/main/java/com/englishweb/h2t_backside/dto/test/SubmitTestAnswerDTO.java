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

    @NotNull(message = "SubmitTest id cannot be null")
    private Long submitTestId;

    @NotNull(message = "Question Id cannot be null")
    private Long questionId;

    @NotNull(message = "Answer Id cannot be null")
    private Long answerId;
}
