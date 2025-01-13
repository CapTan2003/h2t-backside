package com.englishweb.h2t_backside.service.impl;

import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.DiscordNotifier;
import com.englishweb.h2t_backside.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserDTO, User, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository, DiscordNotifier discordNotifier) {
        this.repository = repository;
        this.discordNotifier = discordNotifier;
    }

    @Override
    public boolean verifyOtp(String email, String inputOtp) {
        return false;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("User with ID '%d' not found.", id);
        log.warn(errorMessage);

        this.discordNotifier.buildErrorAndSend(id, errorMessage, ErrorApiCodeContent.USER_NOT_FOUND);
    }

    @Override
    protected void createError(UserDTO dto, Exception ex) {
        log.error("Error creating entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.USER_CREATED_FAIL;

        if(dto.getEmail().isEmpty()) {
            errorMessage = "Email is null or empty";
            errorCode = ErrorApiCodeContent.USER_EMAIL_EMPTY;
        } else if (dto.getName().isEmpty()){
            errorMessage = "Name is null or empty";
            errorCode = ErrorApiCodeContent.USER_NAME_EMPTY;
        } else if (!repository.findAllByEmail(dto.getEmail()).isEmpty()) {
            errorMessage = "Email already exists";
            errorCode = ErrorApiCodeContent.USER_EMAIL_EXIST;
        }

        this.discordNotifier.buildErrorAndSend(dto, errorMessage, errorCode);
    }

    @Override
    protected void updateError(UserDTO dto, Long id, Exception ex) {
        log.error("Error update entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.USER_UPDATED_FAIL;
        // Kiem tra email da ton tai hay chua
        User exitsUser = repository.findAllByEmail(dto.getEmail()).get(0);

        if(dto.getEmail().isEmpty()) {
            errorMessage = "Email is null or empty";
            errorCode = ErrorApiCodeContent.USER_EMAIL_EMPTY;
        } else if (dto.getName().isEmpty()){
            errorMessage = "Name is null or empty";
            errorCode = ErrorApiCodeContent.USER_NAME_EMPTY;
        } else if (!Objects.equals(exitsUser.getId(), id)) {
            errorMessage = "Email already exists";
            errorCode = ErrorApiCodeContent.USER_EMAIL_EXIST;
        }

        this.discordNotifier.buildErrorAndSend(dto, errorMessage, errorCode);
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
