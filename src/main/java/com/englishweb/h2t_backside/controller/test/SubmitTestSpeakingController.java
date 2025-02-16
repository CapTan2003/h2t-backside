package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitTestSpeakingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitTestSpeakingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/submit-test-speaking")
@AllArgsConstructor
public class SubmitTestSpeakingController {

    private final SubmitTestSpeakingService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitTestSpeakingDTO>> create(@Valid @RequestBody SubmitTestSpeakingDTO dto) {
        SubmitTestSpeakingDTO createdSpeaking = service.create(dto);

        ResponseDTO<SubmitTestSpeakingDTO> response = ResponseDTO.<SubmitTestSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdSpeaking)
                .message("SubmitTestSpeaking created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitTestSpeakingDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitTestSpeakingDTO dto) {
        SubmitTestSpeakingDTO updatedSpeaking = service.update(dto, id);

        ResponseDTO<SubmitTestSpeakingDTO> response = ResponseDTO.<SubmitTestSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedSpeaking)
                .message("SubmitTestSpeaking updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitTestSpeakingDTO>> patch(@PathVariable Long id, @RequestBody SubmitTestSpeakingDTO dto) {
        SubmitTestSpeakingDTO patchedSpeaking = service.patch(dto, id);

        ResponseDTO<SubmitTestSpeakingDTO> response = ResponseDTO.<SubmitTestSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedSpeaking)
                .message("SubmitTestSpeaking updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitTestSpeaking deleted successfully" : "Failed to delete SubmitTestSpeaking")
                .build();
        return ResponseEntity.ok(response);
    }
}
