package com.englishweb.h2t_backside.dto.test;

import com.englishweb.h2t_backside.dto.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import com.englishweb.h2t_backside.model.enummodel.TestTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO extends AbstractBaseDTO {

    private String title;
    private String description;
    private Integer duration;
    private TestTypeEnum type;
    private String parts;
    private RouteNodeDTO routeNode;
}
