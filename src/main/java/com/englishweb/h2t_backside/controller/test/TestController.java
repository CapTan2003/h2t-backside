package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.TestDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.TestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/tests")
@AllArgsConstructor
public class TestController {

    private final TestService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<TestDTO>> create(@Valid @RequestBody TestDTO dto) {
        TestDTO createdTest = service.create(dto);

        ResponseDTO<TestDTO> response = ResponseDTO.<TestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdTest)
                .message("Test created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestDTO>> update(@PathVariable Long id, @Valid @RequestBody TestDTO dto) {
        TestDTO updatedTest = service.update(dto, id);

        ResponseDTO<TestDTO> response = ResponseDTO.<TestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedTest)
                .message("Test updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestDTO>> patch(@PathVariable Long id, @RequestBody TestDTO dto) {
        TestDTO patchedTest = service.patch(dto, id);

        ResponseDTO<TestDTO> response = ResponseDTO.<TestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedTest)
                .message("Test updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Test deleted successfully" : "Failed to delete test")
                .build();
        return ResponseEntity.ok(response);
    }
}
