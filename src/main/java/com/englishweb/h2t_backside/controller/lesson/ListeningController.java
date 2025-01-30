package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.ListeningDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.ListeningService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/listenings")
@AllArgsConstructor
public class ListeningController {

    private final ListeningService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ListeningDTO>> findById(@PathVariable Long id) {
        ListeningDTO listening = service.findById(id);

        ResponseDTO<ListeningDTO> response = ResponseDTO.<ListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(listening)
                .message("Listening retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ListeningDTO>> create(@Valid @RequestBody ListeningDTO listeningDTO) {
        ListeningDTO createdListening = service.create(listeningDTO);

        ResponseDTO<ListeningDTO> response = ResponseDTO.<ListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdListening)
                .message("Listening created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ListeningDTO>> update(@PathVariable Long id, @Valid @RequestBody ListeningDTO listeningDTO) {
        ListeningDTO updatedListening = service.update(listeningDTO, id);

        ResponseDTO<ListeningDTO> response = ResponseDTO.<ListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedListening)
                .message("Listening updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ListeningDTO>> patch(@PathVariable Long id, @RequestBody ListeningDTO listeningDTO) {
        ListeningDTO patchedListening = service.patch(listeningDTO, id);

        ResponseDTO<ListeningDTO> response = ResponseDTO.<ListeningDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedListening)
                .message("Listening updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Listening deleted successfully" : "Failed to delete listening")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<ListeningDTO>>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = false) LessonFilterDTO filter) {

        Page<ListeningDTO> listenings = service.searchWithFilters(
                page, size, sortFields, filter);

        ResponseDTO<Page<ListeningDTO>> response = ResponseDTO.<Page<ListeningDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(listenings)
                .message("Listenings retrieved successfully with filters")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/questions")
    public ResponseEntity<ResponseDTO<List<LessonQuestionDTO>>> findQuestionByListeningId(@RequestParam Long listeningId) {
        List<LessonQuestionDTO> questions = service.findQuestionByLessonId(listeningId);
        ResponseDTO<List<LessonQuestionDTO>> response = ResponseDTO.<List<LessonQuestionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(questions)
                .message("Questions retrieved successfully for the listening")
                .build();
        return ResponseEntity.ok(response);
    }
}
