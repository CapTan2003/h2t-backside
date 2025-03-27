package com.englishweb.h2t_backside.service.test;
import com.englishweb.h2t_backside.dto.test.SubmitTestDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

public interface SubmitTestService extends BaseService<SubmitTestDTO> {
    double getScoreOfLastTestByUser(Long userId);
    int countSubmitByUserId(Long userId);
    double totalScoreByUserId(Long userId);
}
