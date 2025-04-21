package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart3_4DTO extends AbstractBaseDTO {

    @NotBlank(message = "Audio URL cannot be blank")
    private String audio;

    @NotBlank(message = "Image URL cannot be blank")
    private String image;

    @NotBlank(message = "Transcript cannot be blank")
    private String transcript;


    private List<Long> questions;
}
