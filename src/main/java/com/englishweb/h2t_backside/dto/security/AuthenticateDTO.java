package com.englishweb.h2t_backside.dto.security;

import com.englishweb.h2t_backside.dto.abstractdto.AbstractBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateDTO extends AbstractBaseDTO {
    private boolean authenticated;
    private String accessToken;
    private String refreshToken;
    private String role;
    private String userId;
}
