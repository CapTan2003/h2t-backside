package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitTestSpeakingDTO extends AbstractBaseDTO {


    @NotNull(message = "submitTest id cannot be null")
    private Long submitTestId;


    @NotNull(message = "Question id cannot be null")
    private Long questionId;

    @NotNull(message = "file cannot be null")
    private String file;

    @NotBlank(message = "Transcript cannot be empty")
    private String transcript;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be at least 0")
    private Double score;
}
