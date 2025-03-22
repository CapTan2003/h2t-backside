package com.englishweb.h2t_backside.controller;
import com.englishweb.h2t_backside.dto.response.SpeechToTextResponse;
import com.englishweb.h2t_backside.service.feature.SpeechToTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/speech-to-text")
@RequiredArgsConstructor
public class SpeechToTextController {

    private final SpeechToTextService speechToTextService;

    @PostMapping("/convert")
    @ResponseStatus(HttpStatus.OK)
    public SpeechToTextResponse speechToText(@RequestParam("audioFile") MultipartFile audioFile) {
        String text = speechToTextService.convertSpeechToText(audioFile);
        return new SpeechToTextResponse(text);
    }

    @PostMapping("/convert-base64")
    @ResponseStatus(HttpStatus.OK)
    public SpeechToTextResponse speechToTextBase64(@RequestBody Map<String, String> request) {
        String base64Audio = request.get("audioBase64");
        String text = speechToTextService.convertSpeechToText(base64Audio);
        return new SpeechToTextResponse(text);
    }
}
