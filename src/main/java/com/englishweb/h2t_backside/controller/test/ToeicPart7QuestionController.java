package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.ToeicPart7QuestionDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.ToeicPart7QuestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/toeic-part7-questions")
@AllArgsConstructor
public class ToeicPart7QuestionController {

    private final ToeicPart7QuestionService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<ToeicPart7QuestionDTO> findById(@PathVariable Long id) {
        ToeicPart7QuestionDTO dto = service.findById(id);
        return ResponseDTO.<ToeicPart7QuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("ToeicPart7Question retrieved successfully")
                .build();
    }
    @PostMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<ToeicPart7QuestionDTO>> getByIds(@RequestBody List<Long> ids) {
        List<ToeicPart7QuestionDTO> result = service.findByIds(ids);
        return ResponseDTO.<List<ToeicPart7QuestionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("Toeic Part 7 questions retrieved successfully")
                .build();
    }



    @PostMapping
    public ResponseEntity<ResponseDTO<ToeicPart7QuestionDTO>> create(@Valid @RequestBody ToeicPart7QuestionDTO dto) {
        ToeicPart7QuestionDTO createdToeicPart7Question = service.create(dto);

        ResponseDTO<ToeicPart7QuestionDTO> response = ResponseDTO.<ToeicPart7QuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdToeicPart7Question)
                .message("ToeicPart7Question created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart7QuestionDTO>> update(@PathVariable Long id, @Valid @RequestBody ToeicPart7QuestionDTO dto) {
        ToeicPart7QuestionDTO updatedToeicPart7Question = service.update(dto, id);

        ResponseDTO<ToeicPart7QuestionDTO> response = ResponseDTO.<ToeicPart7QuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedToeicPart7Question)
                .message("ToeicPart7Question updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart7QuestionDTO>> patch(@PathVariable Long id, @RequestBody ToeicPart7QuestionDTO dto) {
        ToeicPart7QuestionDTO patchedToeicPart7Question = service.patch(dto, id);

        ResponseDTO<ToeicPart7QuestionDTO> response = ResponseDTO.<ToeicPart7QuestionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedToeicPart7Question)
                .message("ToeicPart7Question updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "ToeicPart7Question deleted successfully" : "Failed to delete ToeicPart7Question")
                .build();
        return ResponseEntity.ok(response);
    }
}
