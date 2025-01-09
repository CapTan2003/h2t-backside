package com.englishweb.h2t_backside.model;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic extends AbstractBaseEntity {
    @Column(nullable = false)
    @Comment("Title of the Topic")
    private String title;

    @Column(nullable = false)
    @Comment("Image associated with the Topic")
    private String image;

    @Column(nullable = false)
    @Comment("Description of the Topic")
    private String description;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Comment("Number of views for Lesson")
    private Long views = 0L;

    @Comment("Vocabulary list associated with the Topic")
    private String vocabularies = "";

    @Comment("Questions related to the Topic")
    private String questions = "";

    @ManyToOne
    @JoinColumn(name = "route_id")
    @Comment("Associated Route for the Topic")
    private Route route;
}
