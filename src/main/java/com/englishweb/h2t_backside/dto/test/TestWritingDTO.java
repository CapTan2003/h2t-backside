package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestWritingDTO extends AbstractBaseDTO {

    @NotNull(message = "Topic cannot be null")
    private String topic; // Chủ đề bài viết

    @Min(value = 20, message = "Minimum words must be at least 20")
    private int minWords;

    @Min(value = 20, message = "Maximum words must be at least 20")
    private int maxWords;
}
