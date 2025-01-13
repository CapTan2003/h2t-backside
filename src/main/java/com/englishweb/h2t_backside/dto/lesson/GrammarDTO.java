package com.englishweb.h2t_backside.dto.lesson;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractLessonDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class GrammarDTO extends AbstractLessonDTO {

    @NotBlank(message = "File cannot be empty")
    private String file;

    @NotBlank(message = "Definition cannot be empty")
    private String definition;

    @NotBlank(message = "Example cannot be empty")
    private String example;

    private String questions;
}
