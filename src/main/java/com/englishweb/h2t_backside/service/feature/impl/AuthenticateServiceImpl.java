package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.security.AuthenticateDTO;
import com.englishweb.h2t_backside.dto.security.GoogleLoginDTO;
import com.englishweb.h2t_backside.dto.security.LoginDTO;
import com.englishweb.h2t_backside.exception.AuthenticateException;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.feature.AuthenticateService;
import com.englishweb.h2t_backside.service.feature.UserService;
import com.englishweb.h2t_backside.utils.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticateServiceImpl implements AuthenticateService {
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository repository;
    private final UserService userService;

    @Value("${gg_client_id}")
    private String ggClientId;

    @Autowired
    public AuthenticateServiceImpl(PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserRepository repository, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.repository = repository;
        this.userService = userService;
    }

    public AuthenticateDTO login(LoginDTO dto) {
        User user = repository.findAllByEmail(dto.getEmail())
                .orElseThrow(() -> {
                    log.warn("Invalid email: {}", dto.getEmail());
                    return new ResourceNotFoundException("Invalid email or password.");
                });

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            log.warn("Invalid password for email: {}", dto.getEmail());
            throw new ResourceNotFoundException("Invalid email or password.");
        }

        if(user.getStatus()== StatusEnum.INACTIVE){
            throw new ResourceNotFoundException("Account has been locked!");
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        // Cập nhật refreshToken mới vào database
        user.setRefreshToken(refreshToken);
        repository.save(user);

        log.info("User authenticated successfully: {}", user.getEmail());

        return AuthenticateDTO.builder()
                .authenticated(true)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(user.getRole())
                .userId(Long.toString(user.getId()))
                .build();
    }

    public AuthenticateDTO loginWithGoogle(GoogleLoginDTO request) {
        String idTokenString = request.getIdToken();

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(ggClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new IllegalArgumentException("Invalid Google ID token.");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String avatar = (String) payload.get("picture");

            User user = repository.findAllByEmail(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setAvatar(avatar);
                newUser.setRole(RoleEnum.STUDENT);  // default role
                newUser.setPassword(""); // Không có mật khẩu
                return repository.save(newUser);
            });

            // Sinh token cho user
            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            user.setRefreshToken(refreshToken);
            repository.save(user);

            return AuthenticateDTO.builder()
                    .authenticated(true)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .role(user.getRole())
                    .userId(Long.toString(user.getId()))
                    .build();

        } catch (Exception e) {
            throw new AuthenticateException("Failed to verify Google ID Token", e);
        }
    }

    public void logout(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token is required.");
        }

        User user = repository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid refresh token."));

        // Xóa refresh token khỏi database để vô hiệu hóa nó
        user.setRefreshToken(null);
        repository.save(user);
    }

    public AuthenticateDTO refreshAccessToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token is required");
        }

        // Kiểm tra token hợp lệ
        if (!jwtUtil.validateToken(refreshToken, false)) {
            throw new ResourceNotFoundException("Invalid or expired refresh token.");
        }

        // Kiểm tra xem refresh token có trong DB không
        User user = repository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token is invalid or already used."));

        String newAccessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        // Cập nhật refresh token mới
        user.setRefreshToken(newRefreshToken);
        repository.save(user);

        return AuthenticateDTO.builder()
                .authenticated(true)
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .role(user.getRole())
                .userId(Long.toString(user.getId()))
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

    protected AuthenticateDTO convertToDTO(User user) {
        return AuthenticateDTO.builder()
                .authenticated(true)
                .accessToken(jwtUtil.generateAccessToken(user))
                .refreshToken(jwtUtil.generateRefreshToken(user))
                .role(user.getRole())
                .userId(Long.toString(user.getId()))
                .build();
    }
}
