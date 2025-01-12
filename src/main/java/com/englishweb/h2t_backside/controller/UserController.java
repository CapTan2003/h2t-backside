package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.ResponseDTO;
import com.englishweb.h2t_backside.dto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Endpoints for user management")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @Operation(
            method = "GET",
            summary = "Get user by ID",
            description = "Retrieve a user by their unique ID"
    )
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

    @Operation(
            method = "POST",
            summary = "Create user",
            description = "Create a new user"
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<UserDTO>> create(@RequestBody UserDTO userDTO) {
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

