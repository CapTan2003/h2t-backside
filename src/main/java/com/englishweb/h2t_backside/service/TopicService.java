package com.englishweb.h2t_backside.service;

import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.TopicDTO;
import org.springframework.data.domain.Page;

public interface TopicService extends BaseService<TopicDTO> {

    Page<TopicDTO> searchWithFilters(int page, int size, String sortFields, LessonFilterDTO filter);
}
