package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitToeicPart3_4DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart3_4Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/submit-toeic-part3_4")
@AllArgsConstructor
public class SubmitToeicPart3_4Controller {

    private final SubmitToeicPart3_4Service service;

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitToeicPart3_4DTO>> create(@Valid @RequestBody SubmitToeicPart3_4DTO dto) {
        SubmitToeicPart3_4DTO createdPart3_4 = service.create(dto);

        ResponseDTO<SubmitToeicPart3_4DTO> response = ResponseDTO.<SubmitToeicPart3_4DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPart3_4)
                .message("SubmitToeicPart3_4 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart3_4DTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitToeicPart3_4DTO dto) {
        SubmitToeicPart3_4DTO updatedPart3_4 = service.update(dto, id);

        ResponseDTO<SubmitToeicPart3_4DTO> response = ResponseDTO.<SubmitToeicPart3_4DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPart3_4)
                .message("SubmitToeicPart3_4 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart3_4DTO>> patch(@PathVariable Long id, @RequestBody SubmitToeicPart3_4DTO dto) {
        SubmitToeicPart3_4DTO patchedPart3_4 = service.patch(dto, id);

        ResponseDTO<SubmitToeicPart3_4DTO> response = ResponseDTO.<SubmitToeicPart3_4DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPart3_4)
                .message("SubmitToeicPart3_4 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitToeicPart3_4 deleted successfully" : "Failed to delete SubmitToeicPart3_4")
                .build();
        return ResponseEntity.ok(response);
    }
}
