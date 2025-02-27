package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.security.AuthenticateDTO;
import com.englishweb.h2t_backside.dto.security.LoginDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.feature.AuthenticateService;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthenticateServiceImpl extends BaseServiceImpl<AuthenticateDTO, User, UserRepository> implements AuthenticateService {
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticateServiceImpl(UserRepository repository, DiscordNotifier discordNotifier,
                                   PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        super(repository, discordNotifier);
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("entity with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(AuthenticateDTO dto, Exception ex) {
        log.error("Error creating entity: {}", ex.getMessage());
    }

    @Override
    protected void updateError(AuthenticateDTO dto, Long id, Exception ex) {
        log.error("Error updating entity with ID {}: {}", id, ex.getMessage());
    }

    public ResponseDTO<AuthenticateDTO> login(LoginDTO dto) {
        Optional<User> optionalUser = repository.findAllByEmail(dto.getEmail());

        if (optionalUser.isEmpty()) {
            log.warn("Invalid email: {}", dto.getEmail());
            return ResponseDTO.<AuthenticateDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Invalid email or password.")
                    .data(null)
                    .build();
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            log.warn("Invalid password for email: {}", dto.getEmail());
            return ResponseDTO.<AuthenticateDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Invalid email or password.")
                    .data(null)
                    .build();
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        // Cập nhật refreshToken mới vào database
        user.setRefreshToken(refreshToken);
        repository.save(user);

        log.info("User authenticated successfully: {}", user.getEmail());

        AuthenticateDTO authData = AuthenticateDTO.builder()
                .authenticated(true)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(user.getRoleEnum().name())
                .userId(Long.toString(user.getId()))
                .build();

        return ResponseDTO.<AuthenticateDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("Login successful.")
                .data(authData)
                .build();
    }

    public ResponseDTO<Boolean> logout(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseDTO.<Boolean>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Refresh token is required.")
                    .data(false)
                    .build();
        }

        Optional<User> optionalUser = repository.findByRefreshToken(refreshToken);
        if (optionalUser.isEmpty()) {
            return ResponseDTO.<Boolean>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Invalid refresh token.")
                    .data(false)
                    .build();
        }

        User user = optionalUser.get();

        // Xóa refresh token khỏi database để vô hiệu hóa nó
        user.setRefreshToken(null);
        repository.save(user);

        return ResponseDTO.<Boolean>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("User logged out successfully.")
                .data(true)
                .build();
    }

    public ResponseDTO<AuthenticateDTO> refreshAccessToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseDTO.<AuthenticateDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Refresh token is required")
                    .data(null)
                    .build();
        }

        // Kiểm tra token hợp lệ
        if (!jwtUtil.validateToken(refreshToken, false)) {
            return ResponseDTO.<AuthenticateDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Invalid or expired refresh token.")
                    .data(null)
                    .build();
        }

        // Kiểm tra xem refresh token có trong DB không
        Optional<User> optionalUser = repository.findByRefreshToken(refreshToken);
        if (optionalUser.isEmpty()) {
            return ResponseDTO.<AuthenticateDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Refresh token is invalid or already used.")
                    .data(null)
                    .build();
        }

        User user = optionalUser.get();
        String newAccessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        // Cập nhật refresh token mới
        user.setRefreshToken(newRefreshToken);
        repository.save(user);

        AuthenticateDTO authData = AuthenticateDTO.builder()
                .authenticated(true)
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .role(user.getRoleEnum().name())
                .userId(Long.toString(user.getId()))
                .build();

        return ResponseDTO.<AuthenticateDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("Access token refreshed successfully.")
                .data(authData)
                .build();
    }

    public void updateUserRefreshToken(String userId, String refreshToken) {
        Optional<User> optionalUser = repository.findById(Long.parseLong(userId));

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRefreshToken(refreshToken); // Lưu hoặc xoá refreshToken
            repository.save(user);
        }
    }

    @Override
    protected void patchEntityFromDTO(AuthenticateDTO dto, User entity) { }

    @Override
    protected User convertToEntity(AuthenticateDTO dto) {
        return null;
    }

    @Override
    protected AuthenticateDTO convertToDTO(User user) {
        return AuthenticateDTO.builder()
                .authenticated(true)
                .accessToken(jwtUtil.generateAccessToken(user))
                .refreshToken(jwtUtil.generateRefreshToken(user))
                .role(user.getRoleEnum().name())
                .userId(Long.toString(user.getId()))
                .build();
    }
}
