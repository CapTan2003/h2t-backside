package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.UserMapper;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserDTO, User, UserRepository> implements UserService {
    private final UserMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository repository, DiscordNotifier discordNotifier, UserMapper mapper, PasswordEncoder passwordEncoder) {
        super(repository, discordNotifier);
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
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
    public UserDTO create(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        return super.create(userDTO);
    }

    @Override
    protected void patchEntityFromDTO(UserDTO dto, User entity) { mapper.patchEntityFromDTO(dto, entity); }

    @Override
    protected User convertToEntity(UserDTO dto) { return mapper.convertToEntity(dto); }

    @Override
    protected UserDTO convertToDTO(User entity) { return mapper.convertToDTO(entity); }
}
