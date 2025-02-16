package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.exception.*;
import com.englishweb.h2t_backside.mapper.UserMapper;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.EmailService;
import com.englishweb.h2t_backside.service.feature.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserDTO, User, UserRepository> implements UserService {

    private final UserMapper mapper;
    EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<String, OtpData> otpCache = new HashMap<>();
    private Map<String, Boolean> otpVerifiedCache = new HashMap<>();

    public UserServiceImpl(UserRepository repository, DiscordNotifier discordNotifier, UserMapper mapper, PasswordEncoder passwordEncoder, EmailService emailService) {
        super(repository, discordNotifier);
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.mapper = mapper;
    }

    private class OtpData {
        String otp;
        long timestamp;

        OtpData(String otp, long timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("User with ID '%d' not found.", id);
        log.warn(errorMessage);

        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(UserDTO dto, Exception ex) {
        log.error("Error creating entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.USER_CREATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!repository.findAllByEmail(dto.getEmail()).isEmpty()) {
            errorMessage = "Email already exists";
            errorCode = ErrorApiCodeContent.USER_EMAIL_EXIST;
            status = HttpStatus.BAD_REQUEST;
        }

        throw new CreateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void updateError(UserDTO dto, Long id, Exception ex) {
        log.error("Error update entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.USER_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        // Kiem tra email da ton tai hay chua
        User exitsUser = repository.findAllByEmail(dto.getEmail()).get(0);

        if (!this.isExist(id)){
            errorMessage = String.format("User with ID '%d' not found.", id);
            status =  HttpStatus.NOT_FOUND;
        } else if (!Objects.equals(exitsUser.getId(), id)) {
            errorMessage = "Email already exists";
            errorCode = ErrorApiCodeContent.USER_EMAIL_EXIST;
            status = HttpStatus.BAD_REQUEST;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        emailService.sendPasswordByEmail(userDTO.getEmail(), password);
        return super.create(userDTO);
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
        otpCache.put(email, new OtpData(otp, timestamp)); // Lưu OTP và thời gian tạo
        emailService.sendOtpByEmail(email, otp);
    }

    public boolean verifyOtp(String email, String inputOtp) {
        OtpData otpData = otpCache.get(email.trim());
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
