package com.englishweb.h2t_backside.controller;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.GrammarDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.GrammarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/grammars")
@AllArgsConstructor
public class GrammarController {

    private final GrammarService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<GrammarDTO>> findById(@PathVariable Long id) {
        GrammarDTO grammar = service.findById(id);

        ResponseDTO<GrammarDTO> response = ResponseDTO.<GrammarDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(grammar)
                .message("Grammar retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<GrammarDTO>> create(@Valid @RequestBody GrammarDTO grammarDTO) {
        GrammarDTO createdGrammar = service.create(grammarDTO);

        ResponseDTO<GrammarDTO> response = ResponseDTO.<GrammarDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdGrammar)
                .message("Grammar created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<GrammarDTO>> update(@PathVariable Long id, @Valid @RequestBody GrammarDTO grammarDTO) {
        GrammarDTO updatedGrammar = service.update(grammarDTO, id);

        ResponseDTO<GrammarDTO> response = ResponseDTO.<GrammarDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedGrammar)
                .message("Grammar updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<GrammarDTO>> patch(@PathVariable Long id, @RequestBody GrammarDTO grammarDTO) {
        GrammarDTO patchedGrammar = service.patch(grammarDTO, id);

        ResponseDTO<GrammarDTO> response = ResponseDTO.<GrammarDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedGrammar)
                .message("Grammar updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result =  service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Grammar deleted successfully" : "Failed to delete grammar")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<GrammarDTO>>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = false) LessonFilterDTO filter) {

        Page<GrammarDTO> grammars = service.searchWithFilters(
                page, size, sortFields, filter);

        ResponseDTO<Page<GrammarDTO>> response = ResponseDTO.<Page<GrammarDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(grammars)
                .message("Grammars retrieved successfully with filters")
                .build();
        return ResponseEntity.ok(response);
    }
}
