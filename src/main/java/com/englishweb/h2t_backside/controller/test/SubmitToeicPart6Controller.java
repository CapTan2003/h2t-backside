package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitToeicPart6DTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitToeicPart6Service;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/submit-toeic-part6")
@AllArgsConstructor
public class SubmitToeicPart6Controller {

    private final SubmitToeicPart6Service service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<SubmitToeicPart6DTO> findById(@PathVariable Long id) {
        SubmitToeicPart6DTO dto = service.findById(id);
        return ResponseDTO.<SubmitToeicPart6DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("SubmitToeicPart6 retrieved successfully")
                .build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitToeicPart6DTO>> create(@Valid @RequestBody SubmitToeicPart6DTO dto) {
        SubmitToeicPart6DTO createdPart6 = service.create(dto);

        ResponseDTO<SubmitToeicPart6DTO> response = ResponseDTO.<SubmitToeicPart6DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPart6)
                .message("SubmitToeicPart6 created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart6DTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitToeicPart6DTO dto) {
        SubmitToeicPart6DTO updatedPart6 = service.update(dto, id);

        ResponseDTO<SubmitToeicPart6DTO> response = ResponseDTO.<SubmitToeicPart6DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPart6)
                .message("SubmitToeicPart6 updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicPart6DTO>> patch(@PathVariable Long id, @RequestBody SubmitToeicPart6DTO dto) {
        SubmitToeicPart6DTO patchedPart6 = service.patch(dto, id);

        ResponseDTO<SubmitToeicPart6DTO> response = ResponseDTO.<SubmitToeicPart6DTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPart6)
                .message("SubmitToeicPart6 updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitToeicPart6 deleted successfully" : "Failed to delete SubmitToeicPart6")
                .build();
        return ResponseEntity.ok(response);
    }
}
