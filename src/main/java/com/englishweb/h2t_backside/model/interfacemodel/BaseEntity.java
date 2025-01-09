package com.englishweb.h2t_backside.model.interfacemodel;

import com.englishweb.h2t_backside.model.enummodel.StatusEnum;

import java.time.LocalDateTime;

public interface BaseEntity {
    Long getId();
    void setId(Long id);

    StatusEnum getStatus();
    void setStatus(StatusEnum status);

    LocalDateTime getCreatedAt();
    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getUpdatedAt();
    void setUpdatedAt(LocalDateTime updatedAt);
}
