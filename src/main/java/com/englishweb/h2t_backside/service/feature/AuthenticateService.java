package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.security.AuthenticateDTO;
import com.englishweb.h2t_backside.dto.security.LoginDTO;

public interface AuthenticateService {
    AuthenticateDTO login(LoginDTO dto);

    void logout(String refreshToken);

    AuthenticateDTO refreshAccessToken(String refreshToken);
}
