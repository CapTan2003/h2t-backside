package com.englishweb.h2t_backside.dto;

import com.englishweb.h2t_backside.dto.interfacedto.BaseDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements BaseDTO {
    private Long id;
}
