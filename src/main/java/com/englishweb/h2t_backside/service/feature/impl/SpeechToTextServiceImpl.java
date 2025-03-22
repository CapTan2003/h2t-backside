package com.englishweb.h2t_backside.service.feature.impl;
import com.englishweb.h2t_backside.service.feature.SpeechToTextService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@Service
public class SpeechToTextServiceImpl implements SpeechToTextService {
    @Value("${stt.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public SpeechToTextServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String convertSpeechToText(MultipartFile audioFile) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("audio_file", new ByteArrayResource(audioFile.getBytes()) {
                @Override
                public String getFilename() {
                    return audioFile.getOriginalFilename();
                }
            });

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl + "/asr?task=transcribe&language=en",
                    HttpMethod.POST, requestEntity, String.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error calling Speech-to-Text API", e);
        }
    }
}
