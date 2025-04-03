package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.enummodel.SeverityEnum;
import com.englishweb.h2t_backside.model.interfacemodel.ErrorLogEntity;
import com.englishweb.h2t_backside.model.log.ErrorLog;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ErrorLogSpecification {

    // Tìm theo message (dùng LIKE để tìm kiếm chứa chuỗi)
    public static <T extends ErrorLogEntity> Specification<T> findByMessage(String message) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("message")), "%" + message.toLowerCase() + "%");
    }

    // Tìm theo severity (mức độ nghiêm trọng)
    public static <T extends ErrorLogEntity> Specification<T> findBySeverity(SeverityEnum severity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("severity"), severity);
    }

    // Tìm theo trạng thái fixed (đã được fix chưa)
    public static <T extends ErrorLogEntity> Specification<T> findByFixed(Boolean fixed) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("fixed"), fixed);
    }

    // Tìm theo timestamp (thời gian xảy ra lỗi)
    public static <T extends ErrorLogEntity> Specification<T> findByTimestamp(LocalDateTime timestamp) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("timestamp"), timestamp);
    }
}

