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
        ResponseDTO<String> responseDTO = service.sendOtpForResetPassword(email);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(responseDTO.getStatus())
                .message(responseDTO.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseDTO<String>> verifyOtp(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String otp = requestBody.get("otp");

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

        ResponseDTO<String> responseDTO = service.resetPassword(email, newPassword, confirmPassword);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(responseDTO.getStatus())
                .data(responseDTO.getData())
                .message(responseDTO.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }
}
