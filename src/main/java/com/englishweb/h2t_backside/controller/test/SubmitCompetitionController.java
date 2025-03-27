package com.englishweb.h2t_backside.controller.test;

import com.englishweb.h2t_backside.dto.SubmitTestStatsDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.test.SubmitCompetitionDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.test.SubmitCompetitionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/submit-competitions")
@AllArgsConstructor
public class SubmitCompetitionController {

    private final SubmitCompetitionService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<SubmitCompetitionDTO>> create(@Valid @RequestBody SubmitCompetitionDTO dto) {
        SubmitCompetitionDTO createdCompetition = service.create(dto);

        ResponseDTO<SubmitCompetitionDTO> response = ResponseDTO.<SubmitCompetitionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdCompetition)
                .message("SubmitCompetition created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitCompetitionDTO>> update(@PathVariable Long id, @Valid @RequestBody SubmitCompetitionDTO dto) {
        SubmitCompetitionDTO updatedCompetition = service.update(dto, id);

        ResponseDTO<SubmitCompetitionDTO> response = ResponseDTO.<SubmitCompetitionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedCompetition)
                .message("SubmitCompetition updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SubmitCompetitionDTO>> patch(@PathVariable Long id, @RequestBody SubmitCompetitionDTO dto) {
        SubmitCompetitionDTO patchedCompetition = service.patch(dto, id);

        ResponseDTO<SubmitCompetitionDTO> response = ResponseDTO.<SubmitCompetitionDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedCompetition)
                .message("SubmitCompetition updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "SubmitCompetition deleted successfully" : "Failed to delete SubmitCompetition")
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
