package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.filter.SubmitCompetitionFilterDTO;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubmitCompetitionService extends BaseService<SubmitCompetitionDTO> {

    int countSubmitByUserId(Long userId);

    double totalScoreByUserId(Long userId);

    SubmitCompetitionDTO findByTestIdAndUserIdAndStatusFalse(Long testId, Long userId);

    Page<SubmitCompetitionDTO> searchWithFilters(int page, int size, String sortFields, SubmitCompetitionFilterDTO filter, Long userId);

    List<SubmitCompetitionDTO> getLeaderboard();
}
