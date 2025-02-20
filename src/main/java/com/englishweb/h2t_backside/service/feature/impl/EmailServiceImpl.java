package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.EmailDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.EmailService;
import com.englishweb.h2t_backside.utils.SendEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl extends BaseServiceImpl<EmailDTO, User, UserRepository> implements EmailService {
    private final SendEmail sendEmail;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Map<String, OtpData> otpCache = new HashMap<>();
    private Map<String, Boolean> otpVerifiedCache = new HashMap<>();

    private class OtpData {
        String otp;
        long timestamp;

        OtpData(String otp, long timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }
    }

    public EmailServiceImpl(UserRepository repository, DiscordNotifier discordNotifier, SendEmail sendEmail) {
        super(repository, discordNotifier);
        this.sendEmail = sendEmail;
    }

    @Override
    protected void findByIdError(Long id) { }

    @Override
    protected void createError(EmailDTO dto, Exception ex) { }

    @Override
    protected void updateError(EmailDTO dto, Long id, Exception ex) { }

    private String generateOtp() {
        return String.format("%06d", new SecureRandom().nextInt(1000000));
    }

    public ResponseDTO<String> sendOtpForResetPassword(EmailDTO emailDTO) {
        if (emailDTO.getEmail() == null || emailDTO.getEmail().trim().isEmpty()) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Email field is required.")
                    .build();
        }

        List<User> users = repository.findAllByEmail(emailDTO.getEmail());
        if (users.isEmpty()) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Email does not exist.")
                    .build();
        }

        String otp = generateOtp();
        long timestamp = System.currentTimeMillis();
        otpCache.put(emailDTO.getEmail(), new EmailServiceImpl.OtpData(otp, timestamp));

        sendEmail.sendOtpByEmail(emailDTO.getEmail(), otp);

        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("OTP has been sent successfully.")
                .build();
    }

    public ResponseDTO<Boolean> verifyOtp(EmailDTO emailDTO) {
        EmailServiceImpl.OtpData otpData = otpCache.get(emailDTO.getEmail().trim());
        if (otpData == null) {
            return ResponseDTO.<Boolean>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("OTP code null")
                    .build();
        }

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - otpData.timestamp;
        if (elapsedTime > 120 * 1000) {
            otpCache.remove(emailDTO.getEmail().trim());
            return ResponseDTO.<Boolean>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("OTP code has expired")
                    .build();
        }

        if (!otpData.otp.trim().equals(emailDTO.getOtp().trim())) {
            return ResponseDTO.<Boolean>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("OTP code not valid")
                    .build();
        }

        otpCache.remove(emailDTO.getEmail().trim());
        otpVerifiedCache.put(emailDTO.getEmail().trim(), true);
        return ResponseDTO.<Boolean>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("OTP code is valid")
                .build();
    }

    public ResponseDTO<String> resetPassword(EmailDTO emailDTO) {
        if (!otpVerifiedCache.getOrDefault(emailDTO.getEmail().trim(), false)) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("You need to verify the OTP code before changing your password.")
                    .build();
        }

        if (!emailDTO.getNewPassword().equals(emailDTO.getConfirmPassword())) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("New password and confirm password do not match.")
                    .build();
        }

        List<User> userList = repository.findAllByEmail(emailDTO.getEmail().trim());
        if (userList.isEmpty()) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("User with email '" + emailDTO.getEmail() + "' not found.")
                    .build();
        }

        User user = userList.get(0);
        String hashedPassword = passwordEncoder.encode(emailDTO.getNewPassword());
        user.setPassword(hashedPassword);
        repository.save(user);

        otpVerifiedCache.remove(emailDTO.getEmail().trim());

        return ResponseDTO.<String>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("Password has been reset successfully.")
                .build();
    }

    @Override
    protected void patchEntityFromDTO(EmailDTO dto, User entity) { }

    @Override
    protected User convertToEntity(EmailDTO dto) {
        return null;
    }

    @Override
    protected EmailDTO convertToDTO(User entity) {
        return null;
    }
}
