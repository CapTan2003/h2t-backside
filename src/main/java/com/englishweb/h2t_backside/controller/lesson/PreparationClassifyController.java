package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.PreparationClassifyDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.PreparationClassifyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/preparation-classifies")
@AllArgsConstructor
public class PreparationClassifyController {

    private final PreparationClassifyService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PreparationClassifyDTO>> findById(@PathVariable Long id) {
        PreparationClassifyDTO preparationClassify = service.findById(id);

        ResponseDTO<PreparationClassifyDTO> response = ResponseDTO.<PreparationClassifyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(preparationClassify)
                .message("Preparation classify retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<PreparationClassifyDTO>> create(@Valid @RequestBody PreparationClassifyDTO preparationClassifyDTO) {
        PreparationClassifyDTO createdPreparationClassify = service.create(preparationClassifyDTO);

        ResponseDTO<PreparationClassifyDTO> response = ResponseDTO.<PreparationClassifyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPreparationClassify)
                .message("Preparation classify created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<PreparationClassifyDTO>> update(@PathVariable Long id, @Valid @RequestBody PreparationClassifyDTO preparationClassifyDTO) {
        PreparationClassifyDTO updatedPreparationClassify = service.update(preparationClassifyDTO, id);

        ResponseDTO<PreparationClassifyDTO> response = ResponseDTO.<PreparationClassifyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPreparationClassify)
                .message("Preparation classify updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<PreparationClassifyDTO>> patch(@PathVariable Long id, @RequestBody PreparationClassifyDTO preparationClassifyDTO) {
        PreparationClassifyDTO patchedPreparationClassify = service.patch(preparationClassifyDTO, id);

        ResponseDTO<PreparationClassifyDTO> response = ResponseDTO.<PreparationClassifyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPreparationClassify)
                .message("Preparation classify updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Preparation classify deleted successfully" : "Failed to delete preparation classify")
                .build();
        return ResponseEntity.ok(response);
    }
}
