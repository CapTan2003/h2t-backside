package com.englishweb.h2t_backside.service.homepage;

import com.englishweb.h2t_backside.dto.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.interfacedto.LessonDTO;

import java.util.List;

public interface FeatureLessonService {

    List<RouteNodeDTO> getMostViewedLessons(Integer limit);

    List<RouteNodeDTO> getMostRecentLessons(Integer limit);
}
