package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.SubmitCompetitionDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

public interface SubmitCompetitionService extends BaseService<SubmitCompetitionDTO> {
    int countSubmitByUserId(Long userId);
    double totalScoreByUserId(Long userId);
}
