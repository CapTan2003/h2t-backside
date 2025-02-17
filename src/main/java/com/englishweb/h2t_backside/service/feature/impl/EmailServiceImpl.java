package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.UserMapper;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.EmailService;
import com.englishweb.h2t_backside.utils.SendEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl extends BaseServiceImpl<UserDTO, User, UserRepository> implements EmailService {
    private final UserMapper mapper;
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

    public EmailServiceImpl(UserRepository repository, DiscordNotifier discordNotifier, UserMapper mapper, SendEmail sendEmail) {
        super(repository, discordNotifier);
        this.mapper = mapper;
        this.sendEmail = sendEmail;
    }

    @Override
    protected void findByIdError(Long id) { }

    @Override
    protected void createError(UserDTO dto, Exception ex) {
        log.error("Error creating email entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating email entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.USER_EMAIL_EMPTY;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        throw new CreateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void updateError(UserDTO dto, Long id, Exception ex) {
        log.error("Error updating password entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating password entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.USER_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Email data for user with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    private String generateOtp() {
        return String.format("%06d", new SecureRandom().nextInt(1000000));
    }

    public ResponseDTO<String> sendOtpForResetPassword(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return ResponseDTO.<String>builder()
                        .status(ResponseStatusEnum.FAIL)
                        .message("Email field is required.")
                        .build();
            }

            List<User> users = repository.findAllByEmail(email);
            if (users.isEmpty()) {
                return ResponseDTO.<String>builder()
                        .status(ResponseStatusEnum.FAIL)
                        .message("Email does not exist.")
                        .build();
            }

            String otp = generateOtp();
            long timestamp = System.currentTimeMillis();
            otpCache.put(email, new EmailServiceImpl.OtpData(otp, timestamp));

            sendEmail.sendOtpByEmail(email, otp);

            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("OTP has been sent successfully.")
                    .build();
        } catch (Exception ex) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message(ex.getMessage())
                    .build();
        }
    }

    public boolean verifyOtp(String email, String inputOtp) {
        try {
            EmailServiceImpl.OtpData otpData = otpCache.get(email.trim());
            if (otpData == null) {
                return false;
            }

            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - otpData.timestamp;
            if (elapsedTime > 120 * 1000) {
                otpCache.remove(email.trim());
                return false;
            }

            if (!otpData.otp.trim().equals(inputOtp.trim())) {
                return false;
            }

            otpCache.remove(email.trim());
            otpVerifiedCache.put(email.trim(), true);
            return true;
        } catch (Exception ex) {
            System.err.println("Error verifying OTP for email: " + email + ". Error: " + ex.getMessage());
            return false;
        }
    }

    public ResponseDTO<String> resetPassword(String email, String newPassword, String confirmPassword) {
        try {
            // Kiểm tra OTP đã được xác thực
            if (!otpVerifiedCache.getOrDefault(email.trim(), false)) {
                return ResponseDTO.<String>builder()
                        .status(ResponseStatusEnum.FAIL)
                        .message("You need to verify the OTP code before changing your password.")
                        .build();
            }

            // Kiểm tra mật khẩu mới có khớp với xác nhận mật khẩu không
            if (!newPassword.equals(confirmPassword)) {
                return ResponseDTO.<String>builder()
                        .status(ResponseStatusEnum.FAIL)
                        .message("New password and confirm password do not match.")
                        .build();
            }

            // Tìm người dùng theo email
            List<User> userList = repository.findAllByEmail(email.trim());
            if (userList.isEmpty()) {
                return ResponseDTO.<String>builder()
                        .status(ResponseStatusEnum.FAIL)
                        .message("User with email '" + email + "' not found.")
                        .build();
            }

            // Cập nhật mật khẩu cho user đầu tiên tìm thấy
            User user = userList.get(0);
            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(hashedPassword);
            repository.save(user);

            // Xóa email khỏi cache OTP đã xác thực
            otpVerifiedCache.remove(email.trim());

            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Password has been reset successfully.")
                    .build();
        } catch (Exception ex) {
            return ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message(ex.getMessage())
                    .build();
        }
    }

    @Override
    protected void patchEntityFromDTO(UserDTO dto, User entity) { mapper.patchEntityFromDTO(dto, entity); }

    @Override
    protected User convertToEntity(UserDTO dto) { return mapper.convertToEntity(dto); }

    @Override
    protected UserDTO convertToDTO(User entity) { return mapper.convertToDTO(entity); }
}
