package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.wordnet.VocabularyEnrichmentResponse;

public interface VocabularyEnrichmentService {

    VocabularyEnrichmentResponse analyzeAndEnrichVocabulary(String text);
}
