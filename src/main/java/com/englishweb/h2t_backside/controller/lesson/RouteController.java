package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.RouteDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.RouteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/routes")
@AllArgsConstructor
public class RouteController {

    private final RouteService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<RouteDTO>> findById(@PathVariable Long id) {
        RouteDTO route = service.findById(id);

        ResponseDTO<RouteDTO> response = ResponseDTO.<RouteDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(route)
                .message("Route retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<RouteDTO>> create(@Valid @RequestBody RouteDTO routeDTO) {
        RouteDTO createdRoute = service.create(routeDTO);

        ResponseDTO<RouteDTO> response = ResponseDTO.<RouteDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdRoute)
                .message("Route created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<RouteDTO>> update(@PathVariable Long id, @Valid @RequestBody RouteDTO routeDTO) {
        RouteDTO updatedRoute = service.update(routeDTO, id);

        ResponseDTO<RouteDTO> response = ResponseDTO.<RouteDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedRoute)
                .message("Route updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<RouteDTO>> patch(@PathVariable Long id, @RequestBody RouteDTO routeDTO) {
        RouteDTO patchedRoute = service.patch(routeDTO, id);

        ResponseDTO<RouteDTO> response = ResponseDTO.<RouteDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedRoute)
                .message("Route updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Route deleted successfully" : "Failed to delete route")
                .build();
        return ResponseEntity.ok(response);
    }
}
