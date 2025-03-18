package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Toeic extends AbstractBaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Builder.Default
    private int duration = 120;

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    private String questionsPart1 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    private String questionsPart2 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    private String questionsPart3 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    private String questionsPart4 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    private String questionsPart5 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    private String questionsPart6 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    private String questionsPart7 = "";
}