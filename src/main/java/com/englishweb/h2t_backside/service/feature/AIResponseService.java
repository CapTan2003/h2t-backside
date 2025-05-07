package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.AIResponseDTO;
import com.englishweb.h2t_backside.dto.filter.AIResponseFilterDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AIResponseService extends BaseService<AIResponseDTO>{
    Page<AIResponseDTO> searchWithFilters(int page, int size, String sortFields, AIResponseFilterDTO filter);
}
