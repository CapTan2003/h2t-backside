package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.ReadingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.ReadingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/readings")
@AllArgsConstructor
public class ReadingController {

    private final ReadingService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ReadingDTO>> findById(@PathVariable Long id) {
        ReadingDTO reading = service.findById(id);

        ResponseDTO<ReadingDTO> response = ResponseDTO.<ReadingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(reading)
                .message("Reading retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ReadingDTO>> create(@Valid @RequestBody ReadingDTO readingDTO) {
        ReadingDTO createdReading = service.create(readingDTO);

        ResponseDTO<ReadingDTO> response = ResponseDTO.<ReadingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdReading)
                .message("Reading created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ReadingDTO>> update(@PathVariable Long id, @Valid @RequestBody ReadingDTO readingDTO) {
        ReadingDTO updatedReading = service.update(readingDTO, id);

        ResponseDTO<ReadingDTO> response = ResponseDTO.<ReadingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedReading)
                .message("Reading updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ReadingDTO>> patch(@PathVariable Long id, @RequestBody ReadingDTO readingDTO) {
        ReadingDTO patchedReading = service.patch(readingDTO, id);

        ResponseDTO<ReadingDTO> response = ResponseDTO.<ReadingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedReading)
                .message("Reading updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Reading deleted successfully" : "Failed to delete reading")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<ReadingDTO>>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = false) LessonFilterDTO filter) {

        Page<ReadingDTO> readings = service.searchWithFilters(
                page, size, sortFields, filter);

        ResponseDTO<Page<ReadingDTO>> response = ResponseDTO.<Page<ReadingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(readings)
                .message("Readings retrieved successfully with filters")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/questions")
    public ResponseEntity<ResponseDTO<List<LessonQuestionDTO>>> findQuestionByReadingId(@RequestParam Long readingId) {
        List<LessonQuestionDTO> questions = service.findQuestionByLessonId(readingId);
        ResponseDTO<List<LessonQuestionDTO>> response = ResponseDTO.<List<LessonQuestionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(questions)
                .message("Questions retrieved successfully for the reading")
                .build();
        return ResponseEntity.ok(response);
    }
}
