package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitTestDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.dto.SubmitTestStatsDTO;
import com.englishweb.h2t_backside.service.test.SubmitTestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/submit-tests")
@AllArgsConstructor
public class SubmitTestController {

    private final SubmitTestService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitTestDTO>> create(@Valid @RequestBody SubmitTestDTO dto) {
        SubmitTestDTO createdTest = service.create(dto);

        ResponseDTO<SubmitTestDTO> response = ResponseDTO.<SubmitTestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdTest)
                .message("SubmitTest created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitTestDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitTestDTO dto) {
        SubmitTestDTO updatedTest = service.update(dto, id);

        ResponseDTO<SubmitTestDTO> response = ResponseDTO.<SubmitTestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedTest)
                .message("SubmitTest updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitTestDTO>> patch(@PathVariable Long id, @RequestBody SubmitTestDTO dto) {
        SubmitTestDTO patchedTest = service.patch(dto, id);

        ResponseDTO<SubmitTestDTO> response = ResponseDTO.<SubmitTestDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedTest)
                .message("SubmitTest updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitTest deleted successfully" : "Failed to delete SubmitTest")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseDTO<SubmitTestStatsDTO> getTestStats(@RequestParam Long userId) {
        int count = service.countSubmitByUserId(userId);
        double score = service.totalScoreByUserId(userId);

        SubmitTestStatsDTO stats = new SubmitTestStatsDTO();
        stats.setCount(count);
        stats.setSumScore(score);

        return ResponseDTO.<SubmitTestStatsDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(stats)
                .message("SubmitTest stats retrieved")
                .build();
    }
}
