package com.englishweb.h2t_backside.controller.lesson;

import com.englishweb.h2t_backside.dto.RouteNodeDTO;
import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.lesson.RouteNodeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/route-nodes")
@AllArgsConstructor
public class RouteNodeController {

    private final RouteNodeService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<RouteNodeDTO>> findById(@PathVariable Long id) {
        RouteNodeDTO routeNode = service.findById(id);

        ResponseDTO<RouteNodeDTO> response = ResponseDTO.<RouteNodeDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(routeNode)
                .message("Route node retrieved successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<RouteNodeDTO>> create(@Valid @RequestBody RouteNodeDTO routeNodeDTO) {
        RouteNodeDTO createdRouteNode = service.create(routeNodeDTO);

        ResponseDTO<RouteNodeDTO> response = ResponseDTO.<RouteNodeDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(createdRouteNode)
                .message("Route node created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<RouteNodeDTO>> update(@PathVariable Long id, @Valid @RequestBody RouteNodeDTO routeNodeDTO) {
        RouteNodeDTO updatedRouteNode = service.update(routeNodeDTO, id);

        ResponseDTO<RouteNodeDTO> response = ResponseDTO.<RouteNodeDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(updatedRouteNode)
                .message("Route node updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO<RouteNodeDTO>> patch(@PathVariable Long id, @RequestBody RouteNodeDTO routeNodeDTO) {
        RouteNodeDTO patchedRouteNode = service.patch(routeNodeDTO, id);

        ResponseDTO<RouteNodeDTO> response = ResponseDTO.<RouteNodeDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(patchedRouteNode)
                .message("Route node updated with patch successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Long id) {
        boolean result = service.delete(id);

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .status(result ? ResponseStatusEnum.SUCCESS : ResponseStatusEnum.FAIL)
                .message(result ? "Route node deleted successfully" : "Failed to delete route node")
                .build();
        return ResponseEntity.ok(response);
    }
}
