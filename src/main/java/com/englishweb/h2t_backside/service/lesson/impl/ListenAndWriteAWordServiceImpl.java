package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.ListenAndWriteAWordDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.ListenAndWriteAWordMapper;
import com.englishweb.h2t_backside.model.lesson.ListenAndWriteAWord;
import com.englishweb.h2t_backside.repository.lesson.ListenAndWriteAWordRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.lesson.ListenAndWriteAWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ListenAndWriteAWordServiceImpl
        extends BaseServiceImpl<ListenAndWriteAWordDTO, ListenAndWriteAWord, ListenAndWriteAWordRepository>
        implements ListenAndWriteAWordService {

    private final ListenAndWriteAWordMapper mapper;

    public ListenAndWriteAWordServiceImpl(ListenAndWriteAWordRepository repository,
                                          DiscordNotifierImpl discordNotifier,
                                          ListenAndWriteAWordMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("ListenAndWriteAWord with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(ListenAndWriteAWordDTO dto, Exception ex) {
        log.error("Error creating ListenAndWriteAWord: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating ListenAndWriteAWord: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(ListenAndWriteAWordDTO dto, Long id, Exception ex) {
        log.error("Error updating ListenAndWriteAWord: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating ListenAndWriteAWord: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("ListenAndWriteAWord with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(ListenAndWriteAWordDTO dto, ListenAndWriteAWord entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ListenAndWriteAWord convertToEntity(ListenAndWriteAWordDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ListenAndWriteAWordDTO convertToDTO(ListenAndWriteAWord entity) {
        return mapper.convertToDTO(entity);
    }
}
