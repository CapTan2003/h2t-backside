package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Toeic extends AbstractBaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int duration = 120;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String questionsPart1;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String questionsPart2;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String questionsPart3_4;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String questionsPart5;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String questionsPart6;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String questionsPart7;
}