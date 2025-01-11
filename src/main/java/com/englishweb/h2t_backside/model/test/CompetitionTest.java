package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionTest extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Title of the Competition")
    private String title;

    @Column(nullable = false)
    @Comment("Duration of the Competition in minutes")
    private int duration;

    @Column(nullable = false)
    @Comment("Start time of the Competition")
    private LocalDateTime startTime;

    @Column(nullable = false)
    @Comment("End time of the Competition")
    private LocalDateTime endTime;

    @Column(nullable = false)
    @Comment("Id part of the Competition")
    @ColumnDefault("''")
    private String parts = "";
}
