package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.SpeakingScoreDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.feature.ScoreSpeakingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/score-speaking")
@RequiredArgsConstructor
@Slf4j
public class ScoreSpeakingController {

    private final ScoreSpeakingService scoreSpeakingService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SpeakingScoreDTO> evaluateSpeaking(
            @RequestParam("audio") MultipartFile audioFile,
            @RequestParam("topic") String topic) {

        try {
            SpeakingScoreDTO scoreResult = scoreSpeakingService.evaluateSpeaking(audioFile, topic);

            return ResponseDTO.<SpeakingScoreDTO>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .data(scoreResult)
                    .message("Speaking evaluation completed successfully")
                    .build();
        } catch (IOException e) {
            log.error("Error reading audio file: {}", e.getMessage());
            return ResponseDTO.<SpeakingScoreDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Error reading audio file: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            log.error("Error evaluating speaking: {}", e.getMessage());
            return ResponseDTO.<SpeakingScoreDTO>builder()
                    .status(ResponseStatusEnum.FAIL)
                    .message("Error evaluating speaking: " + e.getMessage())
                    .build();
        }
    }
}