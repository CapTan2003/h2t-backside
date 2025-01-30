package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.SpeakingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.SpeakingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/speakings")
@AllArgsConstructor
public class SpeakingController {

    private final SpeakingService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SpeakingDTO>> findById(@PathVariable Long id) {
        SpeakingDTO speaking = service.findById(id);

        ResponseDTO<SpeakingDTO> response = ResponseDTO.<SpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(speaking)
                .message("Speaking retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SpeakingDTO>> create(@Valid @RequestBody SpeakingDTO speakingDTO) {
        SpeakingDTO createdSpeaking = service.create(speakingDTO);

        ResponseDTO<SpeakingDTO> response = ResponseDTO.<SpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdSpeaking)
                .message("Speaking created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SpeakingDTO>> update(@PathVariable Long id, @Valid @RequestBody SpeakingDTO speakingDTO) {
        SpeakingDTO updatedSpeaking = service.update(speakingDTO, id);

        ResponseDTO<SpeakingDTO> response = ResponseDTO.<SpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedSpeaking)
                .message("Speaking updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SpeakingDTO>> patch(@PathVariable Long id, @RequestBody SpeakingDTO speakingDTO) {
        SpeakingDTO patchedSpeaking = service.patch(speakingDTO, id);

        ResponseDTO<SpeakingDTO> response = ResponseDTO.<SpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedSpeaking)
                .message("Speaking updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Speaking deleted successfully" : "Failed to delete speaking")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<SpeakingDTO>>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = false) LessonFilterDTO filter) {

        Page<SpeakingDTO> speakings = service.searchWithFilters(
                page, size, sortFields, filter);

        ResponseDTO<Page<SpeakingDTO>> response = ResponseDTO.<Page<SpeakingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(speakings)
                .message("Speakings retrieved successfully with filters")
                .build();
        return ResponseEntity.ok(response);
    }
}
