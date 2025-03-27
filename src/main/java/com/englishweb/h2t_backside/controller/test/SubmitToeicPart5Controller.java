package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitToeicPart5DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart5Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/submit-toeic-part5")
@AllArgsConstructor
public class SubmitToeicPart5Controller {

    private final SubmitToeicPart5Service service;

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitToeicPart5DTO>> create(@Valid @RequestBody SubmitToeicPart5DTO dto) {
        SubmitToeicPart5DTO createdPart5 = service.create(dto);

        ResponseDTO<SubmitToeicPart5DTO> response = ResponseDTO.<SubmitToeicPart5DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPart5)
                .message("SubmitToeicPart5 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitToeicPart5DTO> findById(@PathVariable Long id) {
        SubmitToeicPart5DTO dto = service.findById(id);
        return ResponseDTO.<SubmitToeicPart5DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitToeicPart5 retrieved successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart5DTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitToeicPart5DTO dto) {
        SubmitToeicPart5DTO updatedPart5 = service.update(dto, id);

        ResponseDTO<SubmitToeicPart5DTO> response = ResponseDTO.<SubmitToeicPart5DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPart5)
                .message("SubmitToeicPart5 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart5DTO>> patch(@PathVariable Long id, @RequestBody SubmitToeicPart5DTO dto) {
        SubmitToeicPart5DTO patchedPart5 = service.patch(dto, id);

        ResponseDTO<SubmitToeicPart5DTO> response = ResponseDTO.<SubmitToeicPart5DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPart5)
                .message("SubmitToeicPart5 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitToeicPart5 deleted successfully" : "Failed to delete SubmitToeicPart5")
                .build();
        return ResponseEntity.ok(response);
    }
}
