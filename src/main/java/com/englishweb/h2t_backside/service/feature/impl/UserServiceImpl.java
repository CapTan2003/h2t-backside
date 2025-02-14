package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserDTO, User, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository, DiscordNotifier discordNotifier) {
        super(repository, discordNotifier);
    }

    @Override
    public boolean verifyOtp(String email, String inputOtp) {
        return false;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("User with ID '%d' not found.", id);
        log.warn(errorMessage);

        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(UserDTO dto, Exception ex) {
        log.error("Error creating entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.USER_CREATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!repository.findAllByEmail(dto.getEmail()).isEmpty()) {
            errorMessage = "Email already exists";
            errorCode = ErrorApiCodeContent.USER_EMAIL_EXIST;
            status = HttpStatus.BAD_REQUEST;
        }

        throw new CreateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void updateError(UserDTO dto, Long id, Exception ex) {
        log.error("Error update entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.USER_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        // Kiem tra email da ton tai hay chua
        User exitsUser = repository.findAllByEmail(dto.getEmail()).get(0);

        if (!this.isExist(id)){
            errorMessage = String.format("User with ID '%d' not found.", id);
            status =  HttpStatus.NOT_FOUND;
        } else if (!Objects.equals(exitsUser.getId(), id)) {
            errorMessage = "Email already exists";
            errorCode = ErrorApiCodeContent.USER_EMAIL_EXIST;
            status = HttpStatus.BAD_REQUEST;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(UserDTO dto, User entity) {

    }

    @Override
    protected User convertToEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .avatar(dto.getAvatar())
                .email(dto.getEmail())
                .password("123")
                .levelEnum(dto.getLevelEnum())
                .roleEnum(dto.getRoleEnum())
                .phoneNumber(dto.getPhoneNumber())
                .dateOfBirth(dto.getDateOfBirth())
                .status(dto.getStatus() != null ? dto.getStatus() : StatusEnum.ACTIVE)
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
                .phoneNumber(entity.getPhoneNumber())
                .dateOfBirth(entity.getDateOfBirth())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
