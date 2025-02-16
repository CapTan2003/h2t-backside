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
public class SubmitToeicPart5DTO extends AbstractBaseDTO {

    @NotNull(message = "Submit TOEIC cannot be null")
    private SubmitToeicDTO submitToeic;

    @NotNull(message = "Toeic Part 5 cannot be null")
    private ToeicPart5DTO toeicPart5;

    @NotNull(message = "Answer cannot be null")
    private AnswerEnum answer;
}
