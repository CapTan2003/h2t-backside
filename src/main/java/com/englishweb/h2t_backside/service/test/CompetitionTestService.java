package com.englishweb.h2t_backside.service.test;

import com.englishweb.h2t_backside.dto.filter.CompetitionTestFilterDTO;
import com.englishweb.h2t_backside.dto.test.CompetitionTestDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

public interface CompetitionTestService extends BaseService<CompetitionTestDTO> {

    Page<CompetitionTestDTO> searchWithFilters(int page, int size, String sortFields, CompetitionTestFilterDTO filter, Long userId);
    boolean verifyValidCompetitionTest(Long testId);
}
