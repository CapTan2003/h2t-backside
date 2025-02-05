package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.lesson.SpeakingConversationDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.SpeakingConversationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/speaking-conversations")
@AllArgsConstructor
public class SpeakingConversationController {

    private final SpeakingConversationService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SpeakingConversationDTO>> findById(@PathVariable Long id) {
        SpeakingConversationDTO speakingConversation = service.findById(id);

        ResponseDTO<SpeakingConversationDTO> response = ResponseDTO.<SpeakingConversationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(speakingConversation)
                .message("Speaking conversation retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<SpeakingConversationDTO>> create(@Valid @RequestBody SpeakingConversationDTO speakingConversationDTO) {
        SpeakingConversationDTO createdSpeakingConversation = service.create(speakingConversationDTO);

        ResponseDTO<SpeakingConversationDTO> response = ResponseDTO.<SpeakingConversationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdSpeakingConversation)
                .message("Speaking conversation created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<SpeakingConversationDTO>> update(@PathVariable Long id, @Valid @RequestBody SpeakingConversationDTO speakingConversationDTO) {
        SpeakingConversationDTO updatedSpeakingConversation = service.update(speakingConversationDTO, id);

        ResponseDTO<SpeakingConversationDTO> response = ResponseDTO.<SpeakingConversationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedSpeakingConversation)
                .message("Speaking conversation updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<SpeakingConversationDTO>> patch(@PathVariable Long id, @RequestBody SpeakingConversationDTO speakingConversationDTO) {
        SpeakingConversationDTO patchedSpeakingConversation = service.patch(speakingConversationDTO, id);

        ResponseDTO<SpeakingConversationDTO> response = ResponseDTO.<SpeakingConversationDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedSpeakingConversation)
                .message("Speaking conversation updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Speaking conversation deleted successfully" : "Failed to delete speaking conversation")
                .build();
        return ResponseEntity.ok(response);
    }
}
