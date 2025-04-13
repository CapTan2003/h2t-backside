package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.SpeakingScoreDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ScoreSpeakingService {

    SpeakingScoreDTO scoreSpeaking(MultipartFile audioFile);
}
