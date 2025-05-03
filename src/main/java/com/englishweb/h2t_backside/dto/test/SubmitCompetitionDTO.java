package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.dto.UserDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitCompetitionDTO extends AbstractBaseDTO {

    @Min(value = 0, message = "Score must be at least 0")
    private Double score;

    @NotNull(message = "User id cannot be null")
    private Long userId;

    @NotNull(message = "CompetitionTest id be null")
    private Long competition_id;
}
