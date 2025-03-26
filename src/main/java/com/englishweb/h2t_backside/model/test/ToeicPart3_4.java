package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart3_4 extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Audio file for the TOEIC Part 3/4 question")
    private String audio;

    @Column(nullable = false)
    @Comment("Image shown with the question set")
    private String image;

    @Column(nullable = false)
    @Comment("Content of question 1 in the conversation or talk")
    private String contentQuestion1;

    @Column(nullable = false)
    @Comment("Content of question 2 in the conversation or talk")
    private String contentQuestion2;

    @Column(nullable = false)
    @Comment("Content of question 3 in the conversation or talk")
    private String contentQuestion3;

    @Column(nullable = false)
    @Comment("Answer 1 for question 1")
    private String answer1Q1;

    @Column(nullable = false)
    @Comment("Answer 2 for question 1")
    private String answer2Q1;

    @Column(nullable = false)
    @Comment("Answer 3 for question 1")
    private String answer3Q1;

    @Column(nullable = false)
    @Comment("Answer 4 for question 1")
    private String answer4Q1;

    @Column(nullable = false)
    @Comment("Answer 1 for question 2")
    private String answer1Q2;

    @Column(nullable = false)
    @Comment("Answer 2 for question 2")
    private String answer2Q2;

    @Column(nullable = false)
    @Comment("Answer 3 for question 2")
    private String answer3Q2;

    @Column(nullable = false)
    @Comment("Answer 4 for question 2")
    private String answer4Q2;

    @Column(nullable = false)
    @Comment("Answer 1 for question 3")
    private String answer1Q3;

    @Column(nullable = false)
    @Comment("Answer 2 for question 3")
    private String answer2Q3;

    @Column(nullable = false)
    @Comment("Answer 3 for question 3")
    private String answer3Q3;

    @Column(nullable = false)
    @Comment("Answer 4 for question 3")
    private String answer4Q3;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Correct answer for question 1")
    private AnswerEnum correctAnswer1;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Correct answer for question 2")
    private AnswerEnum correctAnswer2;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Correct answer for question 3")
    private AnswerEnum correctAnswer3;
}

