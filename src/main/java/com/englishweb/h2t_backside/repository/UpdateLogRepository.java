package com.englishweb.h2t_backside.repository;

import com.englishweb.h2t_backside.model.log.UpdateLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateLogRepository extends JpaRepository<UpdateLog, Long> {
}
