package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.service.feature.UserService;
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

}

