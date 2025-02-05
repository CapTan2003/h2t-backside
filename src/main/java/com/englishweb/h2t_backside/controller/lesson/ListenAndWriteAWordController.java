package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.ListenAndWriteAWordDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.ListenAndWriteAWordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/listen-and-write-a-word")
@AllArgsConstructor
public class ListenAndWriteAWordController {

    private final ListenAndWriteAWordService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ListenAndWriteAWordDTO>> findById(@PathVariable Long id) {
        ListenAndWriteAWordDTO listenAndWriteAWord = service.findById(id);

        ResponseDTO<ListenAndWriteAWordDTO> response = ResponseDTO.<ListenAndWriteAWordDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(listenAndWriteAWord)
                .message("Listen and write a word retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ListenAndWriteAWordDTO>> create(@Valid @RequestBody ListenAndWriteAWordDTO listenAndWriteAWordDTO) {
        ListenAndWriteAWordDTO createdListenAndWriteAWord = service.create(listenAndWriteAWordDTO);

        ResponseDTO<ListenAndWriteAWordDTO> response = ResponseDTO.<ListenAndWriteAWordDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdListenAndWriteAWord)
                .message("Listen and write a word created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ListenAndWriteAWordDTO>> update(@PathVariable Long id, @Valid @RequestBody ListenAndWriteAWordDTO listenAndWriteAWordDTO) {
        ListenAndWriteAWordDTO updatedListenAndWriteAWord = service.update(listenAndWriteAWordDTO, id);

        ResponseDTO<ListenAndWriteAWordDTO> response = ResponseDTO.<ListenAndWriteAWordDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedListenAndWriteAWord)
                .message("Listen and write a word updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<ListenAndWriteAWordDTO>> patch(@PathVariable Long id, @RequestBody ListenAndWriteAWordDTO listenAndWriteAWordDTO) {
        ListenAndWriteAWordDTO patchedListenAndWriteAWord = service.patch(listenAndWriteAWordDTO, id);

        ResponseDTO<ListenAndWriteAWordDTO> response = ResponseDTO.<ListenAndWriteAWordDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedListenAndWriteAWord)
                .message("Listen and write a word updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Listen and write a word deleted successfully" : "Failed to delete listen and write a word")
                .build();
        return ResponseEntity.ok(response);
    }
}
