package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.feature.UserDTO;
import com.englishweb.h2t_backside.dto.filter.UserFilterDTO;
import com.englishweb.h2t_backside.exception.*;
import com.englishweb.h2t_backside.mapper.UserMapper;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.model.enummodel.RoleEnum;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.EmailService;
import com.englishweb.h2t_backside.service.feature.UserService;
import com.englishweb.h2t_backside.utils.SendEmail;
import com.englishweb.h2t_backside.utils.UserPagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<UserDTO, User, UserRepository> implements UserService {

    private final UserMapper mapper;
    EmailService emailService;
    private final SendEmail sendEmail;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, DiscordNotifier discordNotifier, UserMapper mapper, PasswordEncoder passwordEncoder, EmailService emailService, SendEmail sendEmail) {
        super(repository, discordNotifier);
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.mapper = mapper;
        this.sendEmail = sendEmail;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("User with ID '%d' not found.", id);
        log.warn(errorMessage);

        throw new ResourceNotFoundException(id, errorMessage, SeverityEnum.LOW);
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

        throw new CreateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(UserDTO dto, Long id, Exception ex) {
        log.error("Error update entity: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating entity: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.USER_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        // Kiem tra email da ton tai hay chua
        Optional<User> exitsUser = repository.findAllByEmail(dto.getEmail());

        if (!this.isExist(id)){
            errorMessage = String.format("User with ID '%d' not found.", id);
            status =  HttpStatus.NOT_FOUND;
        } else if (!Objects.equals(exitsUser, id)) {
            errorMessage = "Email already exists";
            errorCode = ErrorApiCodeContent.USER_EMAIL_EXIST;
            status = HttpStatus.BAD_REQUEST;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status, SeverityEnum.LOW);
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        if (userDTO.getRole() == RoleEnum.TEACHER_ADVANCE) {
            sendEmail.sendPasswordByEmail(userDTO.getEmail(), password);
        }
        return super.create(userDTO);
    }

    @Override
    public UserDTO patch(UserDTO dto, Long id) {
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            dto.setPassword(encodedPassword);
            if (dto.getRole() == RoleEnum.TEACHER_ADVANCE) {
                sendEmail.sendPasswordByEmail(dto.getEmail(), password);
            }
        }
        return super.patch(dto, id);
    }

    @Override
    public Page<UserDTO> searchWithFilters(int page, int size, String sortFields, UserFilterDTO filter) {
        return UserPagination.searchWithFiltersGeneric(
                page, size, sortFields, filter, repository, User.class
        ).map(this::convertToDTO);
    }

    @Override
    public List<UserDTO> findByIdInAndStatus(List<Long> ids, Boolean status) {
        List<User> users = repository.findByIdInAndStatus(ids, status);
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }



    @Override
    protected void patchEntityFromDTO(UserDTO dto, User entity) { mapper.patchEntityFromDTO(dto, entity); }

    @Override
    protected User convertToEntity(UserDTO dto) { return mapper.convertToEntity(dto); }

    @Override
    protected UserDTO convertToDTO(User entity) { return mapper.convertToDTO(entity); }
}
