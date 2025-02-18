package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;

public interface EmailService extends BaseService<UserDTO> {
    ResponseDTO<String> sendOtpForResetPassword(String email);

    boolean verifyOtp(String email, String inputOtp);

    ResponseDTO<String>resetPassword(String email, String newPassword, String confirmPassword);
}
