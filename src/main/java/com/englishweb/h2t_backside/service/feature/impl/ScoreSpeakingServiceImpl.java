package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.SpeakingScoreDTO;
import com.englishweb.h2t_backside.service.feature.ScoreSpeakingService;
import com.englishweb.h2t_backside.service.feature.SpeechToTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ScoreSpeakingServiceImpl implements ScoreSpeakingService {

    private final SpeechToTextService speechToTextService;

    public ScoreSpeakingServiceImpl(SpeechToTextService speechToTextService) {
        this.speechToTextService = speechToTextService;
    }

    @Override
    public SpeakingScoreDTO scoreSpeaking(MultipartFile audioFile) {
        SpeakingScoreDTO result = new SpeakingScoreDTO();
        String text = speechToTextService.convertSpeechToText(audioFile);
        result.setTranscript(text);

        String prompt = "";

        return null;
    }
}
