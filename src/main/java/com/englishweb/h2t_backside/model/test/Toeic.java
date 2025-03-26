package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Toeic extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Title of the TOEIC test")
    private String title;

    @Column(nullable = false)
    @Builder.Default
    @Comment("Total duration of the TOEIC test in minutes")
    private int duration = 120;

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 1")
    private String questionsPart1 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 2")
    private String questionsPart2 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 3")
    private String questionsPart3 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 4")
    private String questionsPart4 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 5")
    private String questionsPart5 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 6")
    private String questionsPart6 = "";

    @Column(nullable = false)
    @ColumnDefault("''")
    @Builder.Default
    @Comment("List of question IDs for TOEIC Part 7")
    private String questionsPart7 = "";
}
