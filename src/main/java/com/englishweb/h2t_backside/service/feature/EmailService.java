package com.englishweb.h2t_backside.service.feature;

public interface EmailService {
    void sendOtpByEmail(String toEmail, String otp);
    void sendPasswordByEmail(String toEmail, String password);
}
