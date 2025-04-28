package com.englishweb.h2t_backside.service.homepage;

import com.englishweb.h2t_backside.dto.interfacedto.LessonDTO;

import java.util.List;

public interface FeatureLessonService {

    List<LessonDTO> getMostViewedLessons(Integer limit);

    List<LessonDTO> getMostRecentLessons(Integer limit);
}
