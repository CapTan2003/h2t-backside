package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.UserDTO;

public interface UserService extends BaseService<UserDTO>{
    void sendOtpForResetPassword(String email);

    boolean verifyOtp(String email, String inputOtp);

    void resetPassword(String email, String newPassword, String confirmPassword);
}
