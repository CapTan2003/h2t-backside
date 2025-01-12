package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO user = service.findById(id);
        if (user == null) {
            log.warn("User with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }
}

