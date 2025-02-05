package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.PreparationMakeSentencesDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.PreparationMakeSentencesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/preparation-make-sentences")
@AllArgsConstructor
public class PreparationMakeSentencesController {

    private final PreparationMakeSentencesService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PreparationMakeSentencesDTO>> findById(@PathVariable Long id) {
        PreparationMakeSentencesDTO preparationMakeSentences = service.findById(id);

        ResponseDTO<PreparationMakeSentencesDTO> response = ResponseDTO.<PreparationMakeSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(preparationMakeSentences)
                .message("Preparation make sentences retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<PreparationMakeSentencesDTO>> create(@Valid @RequestBody PreparationMakeSentencesDTO preparationMakeSentencesDTO) {
        PreparationMakeSentencesDTO createdPreparationMakeSentences = service.create(preparationMakeSentencesDTO);

        ResponseDTO<PreparationMakeSentencesDTO> response = ResponseDTO.<PreparationMakeSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdPreparationMakeSentences)
                .message("Preparation make sentences created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<PreparationMakeSentencesDTO>> update(@PathVariable Long id, @Valid @RequestBody PreparationMakeSentencesDTO preparationMakeSentencesDTO) {
        PreparationMakeSentencesDTO updatedPreparationMakeSentences = service.update(preparationMakeSentencesDTO, id);

        ResponseDTO<PreparationMakeSentencesDTO> response = ResponseDTO.<PreparationMakeSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedPreparationMakeSentences)
                .message("Preparation make sentences updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<PreparationMakeSentencesDTO>> patch(@PathVariable Long id, @RequestBody PreparationMakeSentencesDTO preparationMakeSentencesDTO) {
        PreparationMakeSentencesDTO patchedPreparationMakeSentences = service.patch(preparationMakeSentencesDTO, id);

        ResponseDTO<PreparationMakeSentencesDTO> response = ResponseDTO.<PreparationMakeSentencesDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedPreparationMakeSentences)
                .message("Preparation make sentences updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Preparation make sentences deleted successfully" : "Failed to delete preparation make sentences")
                .build();
        return ResponseEntity.ok(response);
    }
}
