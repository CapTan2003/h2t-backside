package com.englishweb.h2t_backside.service.impl;

import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.repository.UserRepository;
import com.englishweb.h2t_backside.service.DiscordNotifier;
import com.englishweb.h2t_backside.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    protected User convertToEntity(UserDTO userDTO) {
        return null;
    }

    @Override
    protected UserDTO convertToDTO(User entity) {
        return null;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("User with ID '%d' not found.", id);
        log.warn(errorMessage);

        String discordPayload = String.format("Timestamp: %s\nError code: %s\nError message: %s\nError Data: %s", System.currentTimeMillis(), ErrorApiCodeContent.USER_NOT_FOUND, errorMessage, id.toString());
        discordNotifier.sendNotification(discordPayload);
    }
}
