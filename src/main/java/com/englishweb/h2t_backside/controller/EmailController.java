package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.EmailDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.feature.EmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class EmailController {
    private final EmailService service;

    @PostMapping("/send-otp")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> sendOtpForResetPassword(@Valid @RequestBody EmailDTO emailDTO) {
        ResponseDTO<String> responseDTO = service.sendOtpForResetPassword(emailDTO);

        return ResponseDTO.<String>builder()
                .status(responseDTO.getStatus())
                .message(responseDTO.getMessage())
                .build();

    }

    @PostMapping("/verify-otp")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<Boolean> verifyOtp(@Valid @RequestBody EmailDTO emailDTO) {
        ResponseDTO<Boolean> response = service.verifyOtp(emailDTO);

        return ResponseDTO.<Boolean>builder()
                .status(response.getStatus())
                .message(response.getMessage())
                .data(response.getData())
                .build();
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> resetPassword(@Valid @RequestBody EmailDTO emailDTO) {

        ResponseDTO<String> responseDTO = service.resetPassword(emailDTO);

        return ResponseDTO.<String>builder()
                .status(responseDTO.getStatus())
                .data(responseDTO.getData())
                .message(responseDTO.getMessage())
                .build();

    }
}
