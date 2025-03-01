package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.EmailDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;

public interface EmailService {
    void sendOtpForResetPassword(EmailDTO emailDTO);

    void verifyOtp(EmailDTO emailDTO);

    void resetPassword(EmailDTO emailDTO);
}
