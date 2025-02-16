package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitToeicDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitToeicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/submit-toeic")
@AllArgsConstructor
public class SubmitToeicController {

    private final SubmitToeicService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitToeicDTO>> create(@Valid @RequestBody SubmitToeicDTO dto) {
        SubmitToeicDTO createdToeic = service.create(dto);

        ResponseDTO<SubmitToeicDTO> response = ResponseDTO.<SubmitToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdToeic)
                .message("SubmitToeic created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitToeicDTO dto) {
        SubmitToeicDTO updatedToeic = service.update(dto, id);

        ResponseDTO<SubmitToeicDTO> response = ResponseDTO.<SubmitToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedToeic)
                .message("SubmitToeic updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitToeicDTO>> patch(@PathVariable Long id, @RequestBody SubmitToeicDTO dto) {
        SubmitToeicDTO patchedToeic = service.patch(dto, id);

        ResponseDTO<SubmitToeicDTO> response = ResponseDTO.<SubmitToeicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedToeic)
                .message("SubmitToeic updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitToeic deleted successfully" : "Failed to delete SubmitToeic")
                .build();
        return ResponseEntity.ok(response);
    }
}
