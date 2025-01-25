package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.ListeningDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

public interface ListeningService extends BaseService<ListeningDTO> {

    Page<ListeningDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter);
}

