package com.englishweb.h2t_backside.dto;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.RouteNodeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RouteNodeDTO extends AbstractBaseDTO {

    @NotBlank(message = "Node id cannot be empty")
    private Long nodeId;

    @NotBlank(message = "Route nodes type cannot be empty")
    private RouteNodeEnum type;

    @Positive(message = "Serial number must be greater than 0")
    private int serial;

    private String title;
    private String description;
    private String image;

    @NotBlank(message = "Route id cannot be empty")
    private Long routeId;
}
