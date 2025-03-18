package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.VoiceDTO;
import com.englishweb.h2t_backside.service.feature.TextToSpeechService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/text-to-speech")
@Slf4j
@AllArgsConstructor
public class TextToSpeechController {

    private final TextToSpeechService service;

    @GetMapping("/convert")
    @ResponseStatus(HttpStatus.OK)
    public InputStreamResource textToSpeech(@RequestParam String text, @RequestParam String voice) throws IOException {
        return service.convertTextToSpeech(text, voice);
    }

    @GetMapping("/voices")
    @ResponseStatus(HttpStatus.OK)
    public List<VoiceDTO> getAvailableVoices() {
        return service.getAvailableVoices();
    }
}
