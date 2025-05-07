package com.englishweb.h2t_backside.dto.admindashboard;

import com.englishweb.h2t_backside.dto.ErrorLogDTO;
import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ErrorLogStatsDTO {
    private long total;
    private Map<SeverityEnum, Long> bySeverity;
    private List<ErrorLogDTO> recentLogs;
}
