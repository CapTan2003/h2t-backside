package com.englishweb.h2t_backside.dto;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.RouteNodeEnum;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class RouteNodeDTO extends AbstractBaseDTO {

    @NotBlank(message = "Node id cannot be empty")
    private Long nodeId;

    @NotBlank(message = "Route nodes type cannot be empty")
    private RouteNodeEnum type;

    @NotBlank(message = "Route id cannot be empty")
    private Long routeId;
}
