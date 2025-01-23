package com.englishweb.h2t_backside.service.feature;

import com.englishweb.h2t_backside.dto.ErrorLogDTO;
import org.springframework.data.domain.Page;

public interface ErrorLogService {
    ErrorLogDTO create(ErrorLogDTO dto);

    Page<ErrorLogDTO> findByPage(int page, int size);
}
