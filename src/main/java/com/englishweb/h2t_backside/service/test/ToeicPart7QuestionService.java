package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.ToeicPart7QuestionDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface ToeicPart7QuestionService extends BaseService<ToeicPart7QuestionDTO> {
    List<ToeicPart7QuestionDTO> findByIds(List<Long> ids);
}
