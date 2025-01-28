package com.englishweb.h2t_backside.service.lesson.impl;

import com.englishweb.h2t_backside.dto.lesson.WritingAnswerDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.lesson.WritingAnswerMapper;
import com.englishweb.h2t_backside.model.lesson.WritingAnswer;
import com.englishweb.h2t_backside.repository.lesson.WritingAnswerRepository;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.feature.impl.DiscordNotifierImpl;
import com.englishweb.h2t_backside.service.lesson.WritingAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WritingAnswerServiceImpl
        extends BaseServiceImpl<WritingAnswerDTO, WritingAnswer, WritingAnswerRepository>
        implements WritingAnswerService {

    private final WritingAnswerMapper mapper;

    public WritingAnswerServiceImpl(WritingAnswerRepository repository,
                                    DiscordNotifierImpl discordNotifier,
                                    WritingAnswerMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        String errorMessage = String.format("WritingAnswer with ID '%d' not found.", id);
        log.warn(errorMessage);
        throw new ResourceNotFoundException(id, errorMessage);
    }

    @Override
    protected void createError(WritingAnswerDTO dto, Exception ex) {
        log.error("Error creating WritingAnswer: {}", ex.getMessage());
        String errorMessage = "Unexpected error creating WritingAnswer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_CREATED_FAIL;
        throw new CreateResourceException(dto, errorMessage, errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void updateError(WritingAnswerDTO dto, Long id, Exception ex) {
        log.error("Error updating WritingAnswer: {}", ex.getMessage());
        String errorMessage = "Unexpected error updating WritingAnswer: " + ex.getMessage();
        String errorCode = ErrorApiCodeContent.LESSON_UPDATED_FAIL;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (!this.isExist(id)) {
            errorMessage = String.format("WritingAnswer with ID '%d' not found.", id);
            status = HttpStatus.NOT_FOUND;
        }

        throw new UpdateResourceException(dto, errorMessage, errorCode, status);
    }

    @Override
    protected void patchEntityFromDTO(WritingAnswerDTO dto, WritingAnswer entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected WritingAnswer convertToEntity(WritingAnswerDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected WritingAnswerDTO convertToDTO(WritingAnswer entity) {
        return mapper.convertToDTO(entity);
    }
}
