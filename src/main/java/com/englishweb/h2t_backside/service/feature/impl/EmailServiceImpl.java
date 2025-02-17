package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.UserDTO;
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

    public void sendOtpForResetPassword(String email) {
        if (email.trim().isEmpty()) {
            throw new ResourceNotFoundException("Email cannot be empty.");
        }
        List<User> user = repository.findAllByEmail(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Email does not exist.");
        }

        String otp = generateOtp();
        long timestamp = System.currentTimeMillis(); // Lấy thời gian hiện tại
        otpCache.put(email, new EmailServiceImpl.OtpData(otp, timestamp)); // Lưu OTP và thời gian tạo
        sendEmail.sendOtpByEmail(email, otp);
    }

    public boolean verifyOtp(String email, String inputOtp) {
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
    }

    public void resetPassword(String email, String newPassword, String confirmPassword) {
        // Kiểm tra OTP đã được xác thực hay chưa
        if (!otpVerifiedCache.getOrDefault(email.trim(), false)) {
            throw new ResourceNotFoundException("You need to verify the OTP code before changing your password.");
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu có khớp nhau không
        if (!newPassword.equals(confirmPassword)) {
            throw new ResourceNotFoundException("New password and confirm password do not match.");
        }

        // Tìm người dùng bằng email
        List<User> userList = repository.findAllByEmail(email.trim());
        if (userList.isEmpty()) {
            throw new ResourceNotFoundException("User with email '" + email + "' not found.");
        }

        // Lấy người dùng đầu tiên từ danh sách
        User user = userList.get(0);

        // Mã hóa mật khẩu mới và cập nhật
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);

        // Lưu người dùng đã cập nhật
        repository.save(user);

        // Xóa email khỏi cache OTP đã xác thực
        otpVerifiedCache.remove(email.trim());
    }

    @Override
    protected void patchEntityFromDTO(UserDTO dto, User entity) { mapper.patchEntityFromDTO(dto, entity); }

    @Override
    protected User convertToEntity(UserDTO dto) { return mapper.convertToEntity(dto); }

    @Override
    protected UserDTO convertToDTO(User entity) { return mapper.convertToDTO(entity); }
}
