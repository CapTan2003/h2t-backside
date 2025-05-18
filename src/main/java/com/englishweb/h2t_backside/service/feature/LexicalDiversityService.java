package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.lexicaldiversity.LexicalDiversityMetrics;

public interface LexicalDiversityService {

    LexicalDiversityMetrics calculateMetrics(String text);
}
