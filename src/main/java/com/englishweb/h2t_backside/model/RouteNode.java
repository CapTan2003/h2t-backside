package com.englishweb.h2t_backside.model;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.RouteNodeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteNode extends AbstractBaseEntity {
    @Column(nullable = false)
    private Long nodeId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RouteNodeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;
}
