package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.WritingDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.WritingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/writings")
@AllArgsConstructor
public class WritingController {

    private final WritingService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<WritingDTO>> findById(@PathVariable Long id) {
        WritingDTO writing = service.findById(id);

        ResponseDTO<WritingDTO> response = ResponseDTO.<WritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(writing)
                .message("Writing retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<WritingDTO>> create(@Valid @RequestBody WritingDTO writingDTO) {
        WritingDTO createdWriting = service.create(writingDTO);

        ResponseDTO<WritingDTO> response = ResponseDTO.<WritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdWriting)
                .message("Writing created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<WritingDTO>> update(@PathVariable Long id, @Valid @RequestBody WritingDTO writingDTO) {
        WritingDTO updatedWriting = service.update(writingDTO, id);

        ResponseDTO<WritingDTO> response = ResponseDTO.<WritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedWriting)
                .message("Writing updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<WritingDTO>> patch(@PathVariable Long id, @RequestBody WritingDTO writingDTO) {
        WritingDTO patchedWriting = service.patch(writingDTO, id);

        ResponseDTO<WritingDTO> response = ResponseDTO.<WritingDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedWriting)
                .message("Writing updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Writing deleted successfully" : "Failed to delete writing")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<WritingDTO>>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = false) LessonFilterDTO filter) {

        Page<WritingDTO> writings = service.searchWithFilters(
                page, size, sortFields, filter);

        ResponseDTO<Page<WritingDTO>> response = ResponseDTO.<Page<WritingDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(writings)
                .message("Writings retrieved successfully with filters")
                .build();
        return ResponseEntity.ok(response);
    }
}
