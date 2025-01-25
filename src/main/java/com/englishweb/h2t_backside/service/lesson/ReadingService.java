package com.englishweb.h2t_backside.service.lesson;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.ReadingDTO;
import com.englishweb.h2t_backside.service.feature.BaseService;
import org.springframework.data.domain.Page;

public interface ReadingService extends BaseService<ReadingDTO> {

    Page<ReadingDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter);
}
