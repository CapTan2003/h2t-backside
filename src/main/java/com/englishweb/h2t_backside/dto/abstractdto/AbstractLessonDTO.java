package com.englishweb.h2t_backside.dto.abstractdto;

import com.englishweb.h2t_backside.model.RouteNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class AbstractLessonDTO extends AbstractBaseDTO{

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Image cannot be empty")
    private String image;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Positive(message = "Views number must be greater than 0")
    private Long views;

    private RouteNode routeNode;
}
