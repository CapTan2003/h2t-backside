package com.englishweb.h2t_backside.model.abstractmodel;

import com.englishweb.h2t_backside.model.Route;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractLessonEntity extends AbstractBaseEntity{
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

    @ManyToOne
    @JoinColumn(name = "route_id")
    @Comment("Associated Route for the Topic")
    private Route route;
}
