package com.englishweb.h2t_backside.controller.feature;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.feature.homepage.QuoteDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.feature.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<QuoteDTO>> getRandomQuote(@RequestParam(required = false) Integer limit) {
        return ResponseDTO.<List<QuoteDTO>>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(service.getRandomQuotes(limit))
                .message("Quotes retrieved successfully")
                .build();
    }
}
