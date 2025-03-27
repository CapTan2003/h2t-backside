package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitToeicPart7DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart7Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/submit-toeic-part7")
@AllArgsConstructor
public class SubmitToeicPart7Controller {

    private final SubmitToeicPart7Service service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitToeicPart7DTO> findById(@PathVariable Long id) {
        SubmitToeicPart7DTO dto = service.findById(id);
        return ResponseDTO.<SubmitToeicPart7DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitToeicPart7 retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitToeicPart7DTO>> create(@Valid @RequestBody SubmitToeicPart7DTO dto) {
        SubmitToeicPart7DTO createdPart7 = service.create(dto);

        ResponseDTO<SubmitToeicPart7DTO> response = ResponseDTO.<SubmitToeicPart7DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPart7)
                .message("SubmitToeicPart7 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart7DTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitToeicPart7DTO dto) {
        SubmitToeicPart7DTO updatedPart7 = service.update(dto, id);

        ResponseDTO<SubmitToeicPart7DTO> response = ResponseDTO.<SubmitToeicPart7DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPart7)
                .message("SubmitToeicPart7 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart7DTO>> patch(@PathVariable Long id, @RequestBody SubmitToeicPart7DTO dto) {
        SubmitToeicPart7DTO patchedPart7 = service.patch(dto, id);

        ResponseDTO<SubmitToeicPart7DTO> response = ResponseDTO.<SubmitToeicPart7DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPart7)
                .message("SubmitToeicPart7 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitToeicPart7 deleted successfully" : "Failed to delete SubmitToeicPart7")
                .build();
        return ResponseEntity.ok(response);
    }
}
