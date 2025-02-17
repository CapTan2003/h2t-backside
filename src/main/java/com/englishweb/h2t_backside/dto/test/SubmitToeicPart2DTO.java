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
public class SubmitToeicPart2DTO extends AbstractBaseDTO {

    @NotNull(message = "Submit TOEIC cannot be null")
    private SubmitToeicDTO submitToeic;

    @NotNull(message = "Toeic Part 2 cannot be null")
    private ToeicPart2DTO toeicPart2;

    @NotNull(message = "Answer cannot be null")
    private AnswerEnum answer;
}
