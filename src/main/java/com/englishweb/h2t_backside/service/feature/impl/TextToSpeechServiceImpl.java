package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.service.feature.TextToSpeechService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class TextToSpeechServiceImpl implements TextToSpeechService {

    @Value("${tts.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public TextToSpeechServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    public InputStreamResource convertTextToSpeech(String inputText, String voice) {
        String model = "kokoro";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("Content-Type", "application/json");

        String requestBody = String.format("{\"model\":\"%s\",\"input\":\"%s\",\"voice\":\"%s\",\"response_format\":\"mp3\",\"download_format\":\"mp3\",\"speed\":1,\"stream\":true,\"return_download_link\":false,\"lang_code\":\"a\",\"normalization_options\":{\"normalize\":true,\"unit_normalization\":false,\"url_normalization\":true,\"email_normalization\":true,\"optional_pluralization_normalization\":true}}",
                model, inputText, voice);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(apiUrl + "/speech", HttpMethod.POST, entity, byte[].class);

            byte[] audioBytes = response.getBody();
            if (audioBytes != null) {
                Path filePath = Paths.get("audio_output.mp3");

                try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile())) {
                    fileOutputStream.write(audioBytes);
                    fileOutputStream.flush();
                } catch (IOException e) {
                    log.error("Error writing to file", e);
                    return null;
                }

                InputStream inputStream = new ByteArrayInputStream(audioBytes);
                return new InputStreamResource(inputStream);
            } else {
                log.error("No audio data received");
                return null;
            }
        } catch (HttpStatusCodeException e) {
            return null;
        }
    }

    public List<String> getAvailableVoices() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Voices> response = restTemplate.exchange(apiUrl + "/voices", HttpMethod.GET, entity, Voices.class);

            Voices responseBody = response.getBody();
            if (responseBody == null) {
                return null;
            }
            return responseBody.voices;
        } catch (HttpStatusCodeException e) {
            return null;
        }
    }
    private record Voices(List<String> voices) {}
}