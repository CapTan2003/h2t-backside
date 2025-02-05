package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.LessonQuestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/lesson-questions")
@AllArgsConstructor
public class LessonQuestionController {

    private final LessonQuestionService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<LessonQuestionDTO>> findById(@PathVariable Long id) {
        LessonQuestionDTO lessonQuestion = service.findById(id);

        ResponseDTO<LessonQuestionDTO> response = ResponseDTO.<LessonQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(lessonQuestion)
                .message("Lesson question retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<LessonQuestionDTO>> create(@Valid @RequestBody LessonQuestionDTO lessonQuestionDTO) {
        LessonQuestionDTO createdLessonQuestion = service.create(lessonQuestionDTO);

        ResponseDTO<LessonQuestionDTO> response = ResponseDTO.<LessonQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdLessonQuestion)
                .message("Lesson question created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<LessonQuestionDTO>> update(@PathVariable Long id, @Valid @RequestBody LessonQuestionDTO lessonQuestionDTO) {
        LessonQuestionDTO updatedLessonQuestion = service.update(lessonQuestionDTO, id);

        ResponseDTO<LessonQuestionDTO> response = ResponseDTO.<LessonQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedLessonQuestion)
                .message("Lesson question updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<LessonQuestionDTO>> patch(@PathVariable Long id, @RequestBody LessonQuestionDTO lessonQuestionDTO) {
        LessonQuestionDTO patchedLessonQuestion = service.patch(lessonQuestionDTO, id);

        ResponseDTO<LessonQuestionDTO> response = ResponseDTO.<LessonQuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedLessonQuestion)
                .message("Lesson question updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Lesson question deleted successfully" : "Failed to delete lesson question")
                .build();
        return ResponseEntity.ok(response);
    }
}
