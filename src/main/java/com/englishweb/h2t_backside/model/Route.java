package com.englishweb.h2t_backside.model;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Route extends AbstractBaseEntity {
    @Lob
    @Comment("Arrays Id of lesson and test")
    private String route = "";  // Lưu mảng Id các bài học, bài kiểm tra trong lộ trình

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
