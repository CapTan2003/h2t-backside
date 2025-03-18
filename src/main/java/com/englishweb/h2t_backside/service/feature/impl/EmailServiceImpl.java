package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.EmailDTO;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.feature.EmailService;
import com.englishweb.h2t_backside.utils.SendEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final SendEmail sendEmail;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final Map<String, OtpData> otpCache = new HashMap<>();
    private final Map<String, Boolean> otpVerifiedCache = new HashMap<>();

    private class OtpData {
        String otp;
        long timestamp;

        OtpData(String otp, long timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }
    }

    public EmailServiceImpl(SendEmail sendEmail, PasswordEncoder passwordEncoder, UserRepository repository) {
        this.sendEmail = sendEmail;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    private String generateOtp() {
        return String.format("%06d", new SecureRandom().nextInt(1000000));
    }

    public void sendOtpForResetPassword(EmailDTO emailDTO) {
        Optional<User> userOptional = repository.findAllByEmail(emailDTO.getEmail());

        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("Email does not exist.");
        }

        String otp = generateOtp();
        long timestamp = System.currentTimeMillis();
        otpCache.put(emailDTO.getEmail(), new EmailServiceImpl.OtpData(otp, timestamp));

        sendEmail.sendOtpByEmail(emailDTO.getEmail(), otp);
    }

    public void verifyOtp(EmailDTO emailDTO) {
        String email = emailDTO.getEmail().trim();
        EmailServiceImpl.OtpData otpData = otpCache.get(email);

        if (otpData == null) {
            throw new ResourceNotFoundException("OTP code is null.");
        }

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - otpData.timestamp;

        if (elapsedTime > 120 * 1000) {
            otpCache.remove(email);
            throw new ResourceNotFoundException("OTP code has expired.");
        }

        if (!otpData.otp.trim().equals(emailDTO.getOtp().trim())) {
            throw new ResourceNotFoundException("OTP code is not valid.");
        }

        otpCache.remove(email);
        otpVerifiedCache.put(email, true);
    }

    public void resetPassword(EmailDTO emailDTO) {
        if (!otpVerifiedCache.getOrDefault(emailDTO.getEmail().trim(), false)) {
            throw new ResourceNotFoundException("You need to verify the OTP code before changing your password.");
        }

        User user = repository.findAllByEmail(emailDTO.getEmail().trim())
                .orElseThrow(() -> new ResourceNotFoundException("User with email '" + emailDTO.getEmail() + "' not found."));

        String hashedPassword = passwordEncoder.encode(emailDTO.getNewPassword());
        user.setPassword(hashedPassword);
        repository.save(user);

        otpVerifiedCache.remove(emailDTO.getEmail().trim());
    }

}
