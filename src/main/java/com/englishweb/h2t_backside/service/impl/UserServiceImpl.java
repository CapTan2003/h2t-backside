package com.englishweb.h2t_backside.service.impl;

import com.englishweb.h2t_backside.dto.ErrorDTO;
import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.DiscordNotifier;
import com.englishweb.h2t_backside.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserDTO, User, UserRepository> implements UserService {

    private final DiscordNotifier discordNotifier;
    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository repository, DiscordNotifier discordNotifier, ObjectMapper objectMapper) {
        this.repository = repository;
        this.discordNotifier = discordNotifier;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean verifyOtp(String email, String inputOtp) {
        return false;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("User with ID '%d' not found.", id);
        log.warn(errorMessage);

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .errorCode(ErrorApiCodeContent.USER_NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .data(id)
                .build();

        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String discordPayload = objectMapper.writeValueAsString(errorDTO);
            discordNotifier.sendNotification("```json\n" + discordPayload + "\n```");
        } catch (Exception e) {
            log.error("Error converting ErrorDTO to JSON: ", e);
        }
    }

    @Override
    protected void createError(UserDTO dto, Exception ex) {
        log.error("Error creating entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating entity: " + ex.getMessage();

        if(dto.getEmail().isEmpty()) {
            errorMessage = "Email is null or empty";
        } else if (dto.getName().isEmpty()){
            errorMessage = "Name is null or empty";
        } else if (!repository.findAllByEmail(dto.getEmail()).isEmpty()) {
            errorMessage = "Email already exists";
        }

        ErrorDTO errorDTO = ErrorDTO.builder()
                .message(errorMessage)
                .errorCode(ErrorApiCodeContent.USER_CREATED_FAIL)
                .timestamp(LocalDateTime.now())
                .data(dto)
                .build();

        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String discordPayload = objectMapper.writeValueAsString(errorDTO);
            discordNotifier.sendNotification("```json\n" + discordPayload + "\n```");
        } catch (Exception e) {
            log.error("Error converting ErrorDTO to JSON: ", e);
        }
    }

    @Override
    protected void updateError(UserDTO dto, Long id, Exception e) {

    }

    @Override
    protected User convertToEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .avatar(userDTO.getAvatar())
                .email(userDTO.getEmail())
                .password("123")
                .levelEnum(userDTO.getLevelEnum())
                .roleEnum(userDTO.getRoleEnum())
                .status(userDTO.getStatus() != null ? userDTO.getStatus() : StatusEnum.ACTIVE)
                .build();
    }

    @Override
    protected UserDTO convertToDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .avatar(entity.getAvatar())
                .email(entity.getEmail())
                .levelEnum(entity.getLevelEnum())
                .roleEnum(entity.getRoleEnum())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
