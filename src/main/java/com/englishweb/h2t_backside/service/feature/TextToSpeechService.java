package com.englishweb.h2t_backside.service.feature;

import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.util.List;

public interface TextToSpeechService {

    InputStreamResource convertTextToSpeech(String inputText, String voice);

    List<String> getAvailableVoices();
}
