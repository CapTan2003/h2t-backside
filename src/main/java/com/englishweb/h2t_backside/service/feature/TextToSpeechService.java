package com.englishweb.h2t_backside.service.feature;

import org.springframework.core.io.InputStreamResource;

import java.io.IOException;

public interface TextToSpeechService {

    InputStreamResource convertTextToSpeech(String text, String voice) throws IOException, InterruptedException;

    String getAvailableVoices() throws IOException, InterruptedException;
}
