package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.EmailDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;

public interface EmailService extends BaseService<EmailDTO> {
    ResponseDTO<String> sendOtpForResetPassword(EmailDTO emailDTO);

    ResponseDTO<Boolean> verifyOtp(EmailDTO emailDTO);

    ResponseDTO<String> resetPassword(EmailDTO emailDTO);
}
