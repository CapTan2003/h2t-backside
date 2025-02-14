package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.service.feature.TextToSpeechService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/text-to-speech")
@Slf4j
@AllArgsConstructor
public class TextToSpeechController {

    private final TextToSpeechService service;

    @GetMapping("/convert")
    public ResponseEntity<InputStreamResource> convertTextToSpeech(
            @RequestParam String text,
            @RequestParam String voice) throws IOException, InterruptedException {
        log.info("Received request to convert text to speech: {} with voice: {}", text, voice);

        InputStreamResource audio = service.convertTextToSpeech(text, voice);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"output.mp3\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");

        return new ResponseEntity<>(audio, headers, HttpStatus.OK);
    }

    @GetMapping("/voices")
    public ResponseEntity<String> getVoices() throws IOException, InterruptedException {
        String voices = service.getAvailableVoices();
        return ResponseEntity.ok(voices);
    }

}
