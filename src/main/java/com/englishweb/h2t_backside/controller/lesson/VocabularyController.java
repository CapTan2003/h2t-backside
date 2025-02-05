package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.VocabularyFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.VocabularyDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.VocabularyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/vocabularies")
@AllArgsConstructor
public class VocabularyController {

    private final VocabularyService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<VocabularyDTO>> findById(@PathVariable Long id) {
        VocabularyDTO vocabulary = service.findById(id);

        ResponseDTO<VocabularyDTO> response = ResponseDTO.<VocabularyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(vocabulary)
                .message("Vocabulary retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<VocabularyDTO>> create(@Valid @RequestBody VocabularyDTO vocabularyDTO) {
        VocabularyDTO createdVocabulary = service.create(vocabularyDTO);

        ResponseDTO<VocabularyDTO> response = ResponseDTO.<VocabularyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdVocabulary)
                .message("Vocabulary created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<VocabularyDTO>> update(@PathVariable Long id, @Valid @RequestBody VocabularyDTO vocabularyDTO) {
        VocabularyDTO updatedVocabulary = service.update(vocabularyDTO, id);

        ResponseDTO<VocabularyDTO> response = ResponseDTO.<VocabularyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedVocabulary)
                .message("Vocabulary updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<VocabularyDTO>> patch(@PathVariable Long id, @RequestBody VocabularyDTO vocabularyDTO) {
        VocabularyDTO patchedVocabulary = service.patch(vocabularyDTO, id);

        ResponseDTO<VocabularyDTO> response = ResponseDTO.<VocabularyDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedVocabulary)
                .message("Vocabulary updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Vocabulary deleted successfully" : "Failed to delete vocabulary")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<VocabularyDTO>>> searchWithFiltersTopicId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = false) VocabularyFilterDTO filter,
            @RequestParam Long topicId) {

        Page<VocabularyDTO> vocabularies = service.searchWithFiltersTopicId(page, size, sortFields, filter, topicId);

        ResponseDTO<Page<VocabularyDTO>> response = ResponseDTO.<Page<VocabularyDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(vocabularies)
                .message("Vocabularies retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
