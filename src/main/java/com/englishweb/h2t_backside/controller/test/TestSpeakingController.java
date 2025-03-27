package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.TestSpeakingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.TestSpeakingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/test-speaking")
@AllArgsConstructor
public class TestSpeakingController {

    private final TestSpeakingService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<TestSpeakingDTO> findById(@PathVariable Long id) {
        TestSpeakingDTO dto = service.findById(id);
        return ResponseDTO.<TestSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(dto)
                .message("TestSpeaking retrieved successfully")
                .build();
    }
    @PostMapping("/by-ids")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<TestSpeakingDTO>> getByIds(@RequestBody List<Long> ids) {
        List<TestSpeakingDTO> result = service.findByIds(ids);
        return ResponseDTO.<List<TestSpeakingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(result)
                .message("TestSpeaking retrieved successfully")
                .build();
    }



    @PostMapping
    public ResponseEntity<ResponseDTO<TestSpeakingDTO>> create(@Valid @RequestBody TestSpeakingDTO dto) {
        TestSpeakingDTO createdTestSpeaking = service.create(dto);

        ResponseDTO<TestSpeakingDTO> response = ResponseDTO.<TestSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdTestSpeaking)
                .message("TestSpeaking created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestSpeakingDTO>> update(@PathVariable Long id, @Valid @RequestBody TestSpeakingDTO dto) {
        TestSpeakingDTO updatedTestSpeaking = service.update(dto, id);

        ResponseDTO<TestSpeakingDTO> response = ResponseDTO.<TestSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedTestSpeaking)
                .message("TestSpeaking updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestSpeakingDTO>> patch(@PathVariable Long id, @RequestBody TestSpeakingDTO dto) {
        TestSpeakingDTO patchedTestSpeaking = service.patch(dto, id);

        ResponseDTO<TestSpeakingDTO> response = ResponseDTO.<TestSpeakingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedTestSpeaking)
                .message("TestSpeaking updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "TestSpeaking deleted successfully" : "Failed to delete test speaking")
                .build();
        return ResponseEntity.ok(response);
    }
}
