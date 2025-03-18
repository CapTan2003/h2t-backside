package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.VoiceDTO;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.util.List;

public interface TextToSpeechService {

    InputStreamResource convertTextToSpeech(String inputText, String voice);

    List<VoiceDTO> getAvailableVoices();
}
