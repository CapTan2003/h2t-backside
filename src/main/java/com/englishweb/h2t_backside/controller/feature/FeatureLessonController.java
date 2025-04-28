package com.englishweb.h2t_backside.controller.feature;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.interfacedto.LessonDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.homepage.FeatureLessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feature-lesson")
@RequiredArgsConstructor
public class FeatureLessonController {

    private final FeatureLessonService service;

    @GetMapping("/most-recent")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<LessonDTO>> getMostRecentLessons(@RequestParam(required = false) Integer limit) {
        return ResponseDTO.<List<LessonDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(service.getMostRecentLessons(limit))
                .message("Feature lessons retrieved successfully")
                .build();
    }

    @GetMapping("/most-viewed")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<LessonDTO>> getMostViewedLessons(@RequestParam(required = false) Integer limit) {
        return ResponseDTO.<List<LessonDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(service.getMostViewedLessons(limit))
                .message("Feature lessons retrieved successfully")
                .build();
    }
}
