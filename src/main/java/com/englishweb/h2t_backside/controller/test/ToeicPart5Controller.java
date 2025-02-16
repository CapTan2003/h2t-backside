package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.ToeicPart5DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.ToeicPart5Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/toeic-part5")
@AllArgsConstructor
public class ToeicPart5Controller {

    private final ToeicPart5Service service;

    @PostMapping
    public ResponseEntity<ResponseDTO<ToeicPart5DTO>> create(@Valid @RequestBody ToeicPart5DTO dto) {
        ToeicPart5DTO createdToeicPart5 = service.create(dto);

        ResponseDTO<ToeicPart5DTO> response = ResponseDTO.<ToeicPart5DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdToeicPart5)
                .message("ToeicPart5 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart5DTO>> update(@PathVariable Long id, @Valid @RequestBody ToeicPart5DTO dto) {
        ToeicPart5DTO updatedToeicPart5 = service.update(dto, id);

        ResponseDTO<ToeicPart5DTO> response = ResponseDTO.<ToeicPart5DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedToeicPart5)
                .message("ToeicPart5 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ToeicPart5DTO>> patch(@PathVariable Long id, @RequestBody ToeicPart5DTO dto) {
        ToeicPart5DTO patchedToeicPart5 = service.patch(dto, id);

        ResponseDTO<ToeicPart5DTO> response = ResponseDTO.<ToeicPart5DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedToeicPart5)
                .message("ToeicPart5 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "ToeicPart5 deleted successfully" : "Failed to delete ToeicPart5")
                .build();
        return ResponseEntity.ok(response);
    }
}
