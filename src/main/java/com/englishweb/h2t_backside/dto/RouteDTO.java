package com.englishweb.h2t_backside.dto;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class RouteDTO extends AbstractBaseDTO {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Image cannot be empty")
    private String image;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    private List<RouteNodeDTO> routeNodes;

    @NotBlank(message = "Owner id cannot be empty")
    private Long ownerId;
}
