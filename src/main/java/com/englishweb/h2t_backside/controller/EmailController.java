package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.service.feature.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class EmailController {
    private final EmailService service;

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
