package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.CompetitionEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TestPartDTO extends AbstractBaseDTO {

    @NotNull(message = "Questions cannot be null")
    private String questions;

    @NotNull(message = "Type cannot be null")
    private CompetitionEnum type;
}
