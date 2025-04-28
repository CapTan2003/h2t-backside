package com.englishweb.h2t_backside.controller.feature;

import com.englishweb.h2t_backside.dto.enumdto.ResponseStatusEnum;
import com.englishweb.h2t_backside.dto.homepage.HeroInfoDTO;
import com.englishweb.h2t_backside.dto.response.ResponseDTO;
import com.englishweb.h2t_backside.service.feature.HeroInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hero-info")
@RequiredArgsConstructor
public class HeroInfoController {

    private final HeroInfoService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<HeroInfoDTO> getHeroInfo() {
        return ResponseDTO.<HeroInfoDTO>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .data(service.getHeroInfo())
                .message("Hero info retrieved successfully")
                .build();
    }
}
