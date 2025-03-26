package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToeic extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Score achieved in the TOEIC test")
    private Integer score;

    @Lob
    @Column(nullable = false)
    @Comment("Comment provided by the reviewer or system")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeic_id")
    @Comment("Reference to the related TOEIC test")
    private Toeic toeic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Comment("User who submitted the TOEIC test")
    private User user;
}

