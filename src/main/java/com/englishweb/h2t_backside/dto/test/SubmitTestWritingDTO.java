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
public class SubmitTestWritingDTO extends AbstractBaseDTO {

    @NotNull(message = "submitTest id cannot be null")
    private Long submitTestId;

    @NotNull(message = "testWriting id test cannot be null")
    private Long testWritingId;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    @NotNull(message = "Score cannot be null")
    @Min(value = 0, message = "Score must be at least 0")
    private Double score;
}
