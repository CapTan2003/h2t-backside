package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.LessonAnswerDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.LessonAnswerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/lesson-answers")
@AllArgsConstructor
public class LessonAnswerController {

    private final LessonAnswerService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<LessonAnswerDTO>> findById(@PathVariable Long id) {
        LessonAnswerDTO lessonAnswer = service.findById(id);

        ResponseDTO<LessonAnswerDTO> response = ResponseDTO.<LessonAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(lessonAnswer)
                .message("Lesson answer retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<LessonAnswerDTO>> create(@Valid @RequestBody LessonAnswerDTO lessonAnswerDTO) {
        LessonAnswerDTO createdLessonAnswer = service.create(lessonAnswerDTO);

        ResponseDTO<LessonAnswerDTO> response = ResponseDTO.<LessonAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdLessonAnswer)
                .message("Lesson answer created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<LessonAnswerDTO>> update(@PathVariable Long id, @Valid @RequestBody LessonAnswerDTO lessonAnswerDTO) {
        LessonAnswerDTO updatedLessonAnswer = service.update(lessonAnswerDTO, id);

        ResponseDTO<LessonAnswerDTO> response = ResponseDTO.<LessonAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedLessonAnswer)
                .message("Lesson answer updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<LessonAnswerDTO>> patch(@PathVariable Long id, @RequestBody LessonAnswerDTO lessonAnswerDTO) {
        LessonAnswerDTO patchedLessonAnswer = service.patch(lessonAnswerDTO, id);

        ResponseDTO<LessonAnswerDTO> response = ResponseDTO.<LessonAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedLessonAnswer)
                .message("Lesson answer updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Lesson answer deleted successfully" : "Failed to delete lesson answer")
                .build();
        return ResponseEntity.ok(response);
    }
}