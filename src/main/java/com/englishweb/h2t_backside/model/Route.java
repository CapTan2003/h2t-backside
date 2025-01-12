package com.englishweb.h2t_backside.model;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route extends AbstractBaseEntity {
    @Column(nullable = false)
    @Comment("Image url for Route")
    private String image;

    @Column(nullable = false)
    @Comment("Description for route")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @Comment("Owner of this route")
    private User owner;
}
