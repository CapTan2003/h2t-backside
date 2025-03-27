package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.test.SubmitToeicDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
public interface SubmitToeicService extends BaseService<SubmitToeicDTO>{
    Double getScoreOfLastTestByUser(Long userId);
    int countSubmitByUserId(Long userId);
    double totalScoreByUserId(Long userId);
}
