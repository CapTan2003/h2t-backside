package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.RouteDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.RouteMapper;
import com.englishweb.h2t_backside.model.Route;
import com.englishweb.h2t_backside.repository.RouteRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.lesson.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RouteServiceImpl extends BaseServiceImpl<RouteDTO, Route, RouteRepository> implements RouteService {

    RouteMapper mapper;

    public RouteServiceImpl(RouteRepository repository, RouteMapper mapper, DiscordNotifier discordNotifier) {
        this.repository = repository;
        this.discordNotifier = discordNotifier;
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("Route with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(RouteDTO dto, Exception ex) {
        log.error("Error creating route: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating route: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.ROUTE_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(RouteDTO dto, Long id, Exception ex) {
        log.error("Error updating route: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating route: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.ROUTE_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("Route with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(RouteDTO dto, Route entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected Route convertToEntity(RouteDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected RouteDTO convertToDTO(Route entity) {
        return mapper.convertToDTO(entity);
    }
}
