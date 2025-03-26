package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.RouteNode;
import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.TestTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.ColumnDefault;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class Test extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Title of the Test")
    private String title;

    @Column(nullable = false)
    @Comment("Description of the Test")
    private String description;

    @Column(nullable = false)
    @Comment("Duration of the Test in minutes")
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Comment("Type of the Test (Mixing, Reading, Listening, etc.)")
    @ColumnDefault("'MIXING'")
    private TestTypeEnum type = TestTypeEnum.MIXING;

    @Column(nullable = false)
    @Comment("Id part of the Test")
    @ColumnDefault("''")
    private String parts = "";

}
