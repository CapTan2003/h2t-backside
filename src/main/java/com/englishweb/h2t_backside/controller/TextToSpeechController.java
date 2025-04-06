package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.TextToSpeechDTO;
import com.englishweb.h2t_backside.dto.VoiceDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
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

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public InputStreamResource textToSpeech(@RequestBody TextToSpeechDTO dto) throws IOException {
        return service.convertTextToSpeech(dto.getText(), dto.getVoice());
    }

    @GetMapping("/voices")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<VoiceDTO>> getAvailableVoices() {
        return ResponseDTO.<List<VoiceDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(service.getAvailableVoices())
                .message("Voices retrieved successfully")
                .build();
    }
}
