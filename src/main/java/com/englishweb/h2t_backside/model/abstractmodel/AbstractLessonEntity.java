package com.englishweb.h2t_backside.model.abstractmodel;

import com.englishweb.h2t_backside.model.RouteNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractLessonEntity extends AbstractBaseEntity{
    @Column(nullable = false)
    @Comment("Title of the Lesson")
    private String title;

    @Column(nullable = false)
    @Comment("Image associated with the Lesson")
    private String image;

    @Column(nullable = false)
    @Comment("Description of the Lesson")
    private String description;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Comment("Number of views for Lesson")
    private Long views = 0L;

    @OneToOne
    @JoinColumn(name = "route_node_id")
    @Comment("Associated Route node for the Lesson")
    private RouteNode routeNode;
}
