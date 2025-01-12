package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart3_4 extends AbstractBaseEntity {
    @Column(nullable = false)
    private String audio;

    private String image;

    @Column(nullable = false)
    private String contentQuestion1;

    @Column(nullable = false)
    private String contentQuestion2;

    @Column(nullable = false)
    private String contentQuestion3;

    @Column(nullable = false)
    private String answer1Q1;

    @Column(nullable = false)
    private String answer2Q1;

    @Column(nullable = false)
    private String answer3Q1;

    @Column(nullable = false)
    private String answer4Q1;

    @Column(nullable = false)
    private String answer1Q2;

    @Column(nullable = false)
    private String answer2Q2;

    @Column(nullable = false)
    private String answer3Q2;

    @Column(nullable = false)
    private String answer4Q2;

    @Column(nullable = false)
    private String answer1Q3;

    @Column(nullable = false)
    private String answer2Q3;

    @Column(nullable = false)
    private String answer3Q3;

    @Column(nullable = false)
    private String answer4Q3;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerEnum correctAnswer1;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerEnum correctAnswer2;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerEnum correctAnswer3;
}
