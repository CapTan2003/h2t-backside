package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.WritingAnswerDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.WritingAnswerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/writing-answers")
@AllArgsConstructor
public class WritingAnswerController {

    private final WritingAnswerService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<WritingAnswerDTO>> findById(@PathVariable Long id) {
        WritingAnswerDTO writingAnswer = service.findById(id);

        ResponseDTO<WritingAnswerDTO> response = ResponseDTO.<WritingAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(writingAnswer)
                .message("Writing answer retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<WritingAnswerDTO>> create(@Valid @RequestBody WritingAnswerDTO writingAnswerDTO) {
        WritingAnswerDTO createdWritingAnswer = service.create(writingAnswerDTO);

        ResponseDTO<WritingAnswerDTO> response = ResponseDTO.<WritingAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdWritingAnswer)
                .message("Writing answer created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<WritingAnswerDTO>> update(@PathVariable Long id, @Valid @RequestBody WritingAnswerDTO writingAnswerDTO) {
        WritingAnswerDTO updatedWritingAnswer = service.update(writingAnswerDTO, id);

        ResponseDTO<WritingAnswerDTO> response = ResponseDTO.<WritingAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedWritingAnswer)
                .message("Writing answer updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<WritingAnswerDTO>> patch(@PathVariable Long id, @RequestBody WritingAnswerDTO writingAnswerDTO) {
        WritingAnswerDTO patchedWritingAnswer = service.patch(writingAnswerDTO, id);

        ResponseDTO<WritingAnswerDTO> response = ResponseDTO.<WritingAnswerDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedWritingAnswer)
                .message("Writing answer updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Writing answer deleted successfully" : "Failed to delete writing answer")
                .build();
        return ResponseEntity.ok(response);
    }
}
