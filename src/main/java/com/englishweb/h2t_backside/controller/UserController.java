package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
}

