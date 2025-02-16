package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitTestDTO extends AbstractBaseDTO {

    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be at least 0")
    private Integer score;

    @NotNull(message = "User cannot be null")
    private UserDTO user;

    @NotNull(message = "Test cannot be null")
    private TestDTO test;

    private String comment;
}
