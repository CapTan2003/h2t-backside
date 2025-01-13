package com.englishweb.h2t_backside.repository;

import com.englishweb.h2t_backside.model.log.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
}
