package com.englishweb.h2t_backside.service.test;


import com.englishweb.h2t_backside.dto.test.ToeicPart5DTO;
import com.englishweb.h2t_backside.service.feature.BaseService;

import java.util.List;

public interface ToeicPart5Service extends BaseService<ToeicPart5DTO> {
    List<ToeicPart5DTO> findByIds(List<Long> ids);
}
