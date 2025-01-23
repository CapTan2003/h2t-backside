package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.GrammarDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

public interface GrammarService extends BaseService<GrammarDTO> {

    Page<GrammarDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter);
}
