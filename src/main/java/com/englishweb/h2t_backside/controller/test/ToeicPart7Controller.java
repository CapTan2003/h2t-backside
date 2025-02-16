package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.ToeicPart7DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.ToeicPart7Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/toeic-part7")
@AllArgsConstructor
public class ToeicPart7Controller {

    private final ToeicPart7Service service;

    @PostMapping
    public ResponseEntity<ResponseDTO<ToeicPart7DTO>> create(@Valid @RequestBody ToeicPart7DTO dto) {
        ToeicPart7DTO createdToeicPart7 = service.create(dto);

        ResponseDTO<ToeicPart7DTO> response = ResponseDTO.<ToeicPart7DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdToeicPart7)
                .message("ToeicPart7 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart7DTO>> update(@PathVariable Long id, @Valid @RequestBody ToeicPart7DTO dto) {
        ToeicPart7DTO updatedToeicPart7 = service.update(dto, id);

        ResponseDTO<ToeicPart7DTO> response = ResponseDTO.<ToeicPart7DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedToeicPart7)
                .message("ToeicPart7 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart7DTO>> patch(@PathVariable Long id, @RequestBody ToeicPart7DTO dto) {
        ToeicPart7DTO patchedToeicPart7 = service.patch(dto, id);

        ResponseDTO<ToeicPart7DTO> response = ResponseDTO.<ToeicPart7DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedToeicPart7)
                .message("ToeicPart7 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "ToeicPart7 deleted successfully" : "Failed to delete ToeicPart7")
                .build();
        return ResponseEntity.ok(response);
    }
}
