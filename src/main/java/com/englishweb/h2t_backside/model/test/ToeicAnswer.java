package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Comment;

public class ToeicAnswer extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Content of the answer")
    private String content;

    @Column(nullable = false)
    @Comment("Answer is correct or not")
    private boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeicQuestion_id")
    private ToeicQuestion toeicQuestion;
}
