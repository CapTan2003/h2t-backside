package com.englishweb.h2t_backside.model;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.RouteNodeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class RouteNode extends AbstractBaseEntity {
    private Long nodeId;
    private RouteNodeEnum type;
}
