package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.service.feature.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> findById(@PathVariable Long id) {
        UserDTO user = service.findById(id);
        if (user == null) {
            log.warn("User with ID {} not found", id);
            ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("User not found")
                    .build();
            return ResponseEntity.status(404).body(response);
        }

        ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(user)
                .message("User retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<UserDTO>> create(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = service.create(userDTO);
        if (createdUser == null) {
            ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Failed to create user")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdUser)
                .message("User created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = service.update(userDTO, id);

        ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedUser)
                .message("User updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserDTO>> patch(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO patchedUser = service.patch(userDTO, id);

        ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedUser)
                .message("User updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "User deleted successfully" : "Failed to delete user")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ResponseDTO<String>> sendOtpForResetPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");

        if (email == null || email.trim().isEmpty()) {
            ResponseDTO<String> response = ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Email is required.")
                    .build();
            return ResponseEntity.status(400).body(response);
        }

        try {
            // Gửi OTP cho người dùng
            service.sendOtpForResetPassword(email);

            ResponseDTO<String> response = ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("OTP has been sent to your email successfully.")
                    .build();

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            ResponseDTO<String> response = ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message(ex.getMessage()) // Nếu email không tồn tại, sẽ trả thông báo lỗi
                    .build();

            return ResponseEntity.status(404).body(response);
        } catch (Exception ex) {
            ResponseDTO<String> response = ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Failed to send OTP. Please try again.")
                    .build();

            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseDTO<String>> verifyOtp(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String otp = requestBody.get("otp");

        // Kiểm tra OTP có hợp lệ hay không
        boolean isVerified = service.verifyOtp(email, otp);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(isVerified ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(isVerified ? "OTP verified successfully." : "Invalid OTP or OTP expired.")
                .build();

        return ResponseEntity.status(isVerified ? 200 : 400).body(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDTO<String>> resetPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String newPassword = requestBody.get("newPassword");
        String confirmPassword = requestBody.get("confirmPassword");

        try {
            // Gọi service để reset mật khẩu
            service.resetPassword(email, newPassword, confirmPassword);

            ResponseDTO<String> response = ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Password has been reset successfully.")
                    .build();

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException ex) {
            ResponseDTO<String> response = ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message(ex.getMessage()) // Nếu OTP chưa được xác thực hoặc mật khẩu không hợp lệ
                    .build();

            return ResponseEntity.status(400).body(response);
        } catch (Exception ex) {
            ResponseDTO<String> response = ResponseDTO.<String>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Failed to reset password. Please try again.")
                    .build();

            return ResponseEntity.status(500).body(response);
        }
    }
}

