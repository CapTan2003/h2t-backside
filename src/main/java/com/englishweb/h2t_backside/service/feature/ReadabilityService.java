package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.readability.ReadabilityMetrics;

public interface ReadabilityService {

    ReadabilityMetrics calculateReadabilityMetrics(String text);
}
