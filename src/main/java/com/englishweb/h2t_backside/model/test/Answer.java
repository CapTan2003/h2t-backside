package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;

@Entity
public class Answer extends AbstractBaseEntity {
    private String content;
    private boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    Question question;
}
