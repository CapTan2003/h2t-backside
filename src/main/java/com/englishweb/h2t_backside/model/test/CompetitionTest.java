package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class CompetitionTest extends AbstractBaseEntity {
    private String title;
    private int duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String parts;
}
