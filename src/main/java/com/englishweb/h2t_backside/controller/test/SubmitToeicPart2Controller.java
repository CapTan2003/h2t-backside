package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitToeicPart2DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart2Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/submit-toeic-part2")
@AllArgsConstructor
public class SubmitToeicPart2Controller {

    private final SubmitToeicPart2Service service;

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitToeicPart2DTO>> create(@Valid @RequestBody SubmitToeicPart2DTO dto) {
        SubmitToeicPart2DTO createdPart2 = service.create(dto);

        ResponseDTO<SubmitToeicPart2DTO> response = ResponseDTO.<SubmitToeicPart2DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPart2)
                .message("SubmitToeicPart2 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart2DTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitToeicPart2DTO dto) {
        SubmitToeicPart2DTO updatedPart2 = service.update(dto, id);

        ResponseDTO<SubmitToeicPart2DTO> response = ResponseDTO.<SubmitToeicPart2DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPart2)
                .message("SubmitToeicPart2 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart2DTO>> patch(@PathVariable Long id, @RequestBody SubmitToeicPart2DTO dto) {
        SubmitToeicPart2DTO patchedPart2 = service.patch(dto, id);

        ResponseDTO<SubmitToeicPart2DTO> response = ResponseDTO.<SubmitToeicPart2DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPart2)
                .message("SubmitToeicPart2 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitToeicPart2 deleted successfully" : "Failed to delete SubmitToeicPart2")
                .build();
        return ResponseEntity.ok(response);
    }
}
