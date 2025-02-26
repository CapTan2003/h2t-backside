package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.security.AuthenticateDTO;
import com.englishweb.h2t_backside.dto.security.LoginDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;

public interface AuthenticateService extends BaseService<AuthenticateDTO>{
    ResponseDTO<AuthenticateDTO> login(LoginDTO dto);

    ResponseDTO<Boolean> logout(String token);

    void updateUserRefreshToken(String userId, String refreshToken);

    ResponseDTO<AuthenticateDTO> refreshAccessToken(String refreshToken);
}
