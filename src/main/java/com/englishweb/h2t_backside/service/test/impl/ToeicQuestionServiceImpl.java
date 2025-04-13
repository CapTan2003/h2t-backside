package com.englishweb.h2t_backside.service.test.impl;

import com.englishweb.h2t_backside.dto.test.ToeicQuestionDTO;
import com.englishweb.h2t_backside.exception.CreateResourceException;
import com.englishweb.h2t_backside.exception.ErrorApiCodeContent;
import com.englishweb.h2t_backside.exception.ResourceNotFoundException;
import com.englishweb.h2t_backside.exception.UpdateResourceException;
import com.englishweb.h2t_backside.mapper.test.ToeicQuestionMapper;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.test.ToeicQuestion;
import com.englishweb.h2t_backside.repository.test.ToeicQuestionRepository;
import com.englishweb.h2t_backside.service.feature.DiscordNotifier;
import com.englishweb.h2t_backside.service.feature.impl.BaseServiceImpl;
import com.englishweb.h2t_backside.service.test.ToeicQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ToeicQuestionServiceImpl extends BaseServiceImpl<ToeicQuestionDTO, ToeicQuestion, ToeicQuestionRepository>
        implements ToeicQuestionService {

    private final ToeicQuestionMapper mapper;

    public ToeicQuestionServiceImpl(ToeicQuestionRepository repository,
                                    DiscordNotifier discordNotifier,
                                    ToeicQuestionMapper mapper) {
        super(repository, discordNotifier);
        this.mapper = mapper;
    }

    @Override
    protected void findByIdError(Long id) {
        throw new ResourceNotFoundException(id, "ToeicQuestion not found", SeverityEnum.LOW);
    }

    @Override
    protected void createError(ToeicQuestionDTO dto, Exception ex) {
        throw new CreateResourceException(dto, "Error creating ToeicQuestion: " + ex.getMessage(),
                ErrorApiCodeContent.LESSON_CREATED_FAIL, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void updateError(ToeicQuestionDTO dto, Long id, Exception ex) {
        throw new UpdateResourceException(dto, "Error updating ToeicQuestion: " + ex.getMessage(),
                ErrorApiCodeContent.LESSON_UPDATED_FAIL, HttpStatus.INTERNAL_SERVER_ERROR, SeverityEnum.HIGH);
    }

    @Override
    protected void patchEntityFromDTO(ToeicQuestionDTO dto, ToeicQuestion entity) {
        mapper.patchEntityFromDTO(dto, entity);
    }

    @Override
    protected ToeicQuestion convertToEntity(ToeicQuestionDTO dto) {
        return mapper.convertToEntity(dto);
    }

    @Override
    protected ToeicQuestionDTO convertToDTO(ToeicQuestion entity) {
        return mapper.convertToDTO(entity);
    }

    @Override
    public List<ToeicQuestionDTO> findByIds(List<Long> ids) {
        List<ToeicQuestionDTO> result = new LinkedList<>();
        for (Long id : ids) {
            result.add(findById(id));
        }
        return result;
    }
}
