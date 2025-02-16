package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.TestPartDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.TestPartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/test-parts")
@AllArgsConstructor
public class TestPartController {

    private final TestPartService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<TestPartDTO>> create(@Valid @RequestBody TestPartDTO dto) {
        TestPartDTO createdTestPart = service.create(dto);

        ResponseDTO<TestPartDTO> response = ResponseDTO.<TestPartDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdTestPart)
                .message("TestPart created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestPartDTO>> update(@PathVariable Long id, @Valid @RequestBody TestPartDTO dto) {
        TestPartDTO updatedTestPart = service.update(dto, id);

        ResponseDTO<TestPartDTO> response = ResponseDTO.<TestPartDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedTestPart)
                .message("TestPart updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<TestPartDTO>> patch(@PathVariable Long id, @RequestBody TestPartDTO dto) {
        TestPartDTO patchedTestPart = service.patch(dto, id);

        ResponseDTO<TestPartDTO> response = ResponseDTO.<TestPartDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedTestPart)
                .message("TestPart updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "TestPart deleted successfully" : "Failed to delete test part")
                .build();
        return ResponseEntity.ok(response);
    }
}
