package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToeic extends AbstractBaseEntity {
    @Column(nullable = false)
    private Integer score;

    @Lob
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    private Toeic toeic;

    @ManyToOne
    private User user;
}
