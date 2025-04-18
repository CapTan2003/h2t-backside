package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.WritingScoreDTO;

public interface ScoreWritingService {

    WritingScoreDTO scoreWriting(String text, String topic);
}
