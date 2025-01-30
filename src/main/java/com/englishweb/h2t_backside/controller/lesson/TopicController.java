package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.filter.LessonFilterDTO;
import com.englishweb.h2t_backside.dto.lesson.LessonQuestionDTO;
import com.englishweb.h2t_backside.dto.lesson.TopicDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.TopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/topics")
@AllArgsConstructor
public class TopicController {

    private final TopicService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<TopicDTO>> findById(@PathVariable Long id) {
        TopicDTO topic = service.findById(id);

        ResponseDTO<TopicDTO> response = ResponseDTO.<TopicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(topic)
                .message("Topic retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<TopicDTO>> create(@Valid @RequestBody TopicDTO topicDTO) {
        TopicDTO createdTopic = service.create(topicDTO);

        ResponseDTO<TopicDTO> response = ResponseDTO.<TopicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdTopic)
                .message("Topic created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<TopicDTO>> update(@PathVariable Long id, @Valid @RequestBody TopicDTO topicDTO) {
        TopicDTO updatedTopic = service.update(topicDTO, id);

        ResponseDTO<TopicDTO> response = ResponseDTO.<TopicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedTopic)
                .message("Topic updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<TopicDTO>> patch(@PathVariable Long id, @RequestBody TopicDTO topicDTO) {
        TopicDTO patchedTopic = service.patch(topicDTO, id);

        ResponseDTO<TopicDTO> response = ResponseDTO.<TopicDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedTopic)
                .message("Topic updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Topic deleted successfully" : "Failed to delete topic")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<TopicDTO>>> searchWithFilters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sortFields,
            @RequestParam(required = false) LessonFilterDTO filter) {

        Page<TopicDTO> topics = service.searchWithFilters(
                page, size, sortFields, filter);

        ResponseDTO<Page<TopicDTO>> response = ResponseDTO.<Page<TopicDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(topics)
                .message("Topics retrieved successfully with filters")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/questions")
    public ResponseEntity<ResponseDTO<List<LessonQuestionDTO>>> findQuestionByTopicId(@RequestParam Long topicId) {
        List<LessonQuestionDTO> lessons = service.findQuestionByLessonId(topicId);
        ResponseDTO<List<LessonQuestionDTO>> response = ResponseDTO.<List<LessonQuestionDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(lessons)
                .message("Lessons retrieved successfully for the topic")
                .build();
        return ResponseEntity.ok(response);
    }
}
