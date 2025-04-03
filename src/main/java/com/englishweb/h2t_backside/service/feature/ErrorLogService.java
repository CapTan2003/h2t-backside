package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.ErrorLogDTO;
import com.englishweb.h2t_backside.dto.filter.ErrorLogFilterDTO;
import org.springframework.data.domain.Page;

public interface ErrorLogService {
    ErrorLogDTO create(ErrorLogDTO dto);

    ErrorLogDTO update(Long id, ErrorLogDTO dto);

    Page<ErrorLogDTO> findByPage(int page, int size, String sortFields, ErrorLogFilterDTO filter);
}
