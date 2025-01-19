package com.englishweb.h2t_backside.service;

import com.englishweb.h2t_backside.dto.lesson.GrammarDTO;
import com.englishweb.h2t_backside.model.enummodel.StatusEnum;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface GrammarService extends BaseService<GrammarDTO> {

    Page<GrammarDTO> searchWithFilters(String title, LocalDateTime startCreatedAt, LocalDateTime endCreatedAt, LocalDateTime startUpdatedAt, LocalDateTime endUpdatedAt, int page, int size, String sortFields, StatusEnum status);
}
