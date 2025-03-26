package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart6 extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Path to the DOCX file for the TOEIC Part 6 passage")
    private String file;

    @Column(nullable = false)
    @Comment("Content of question 1 in the paragraph")
    private String contentQuestion1;

    @Column(nullable = false)
    @Comment("Content of question 2 in the paragraph")
    private String contentQuestion2;

    @Column(nullable = false)
    @Comment("Content of question 3 in the paragraph")
    private String contentQuestion3;

    @Column(nullable = false)
    @Comment("Content of question 4 in the paragraph")
    private String contentQuestion4;

    @Column(nullable = false)
    @Comment("First answer choice for question 1")
    private String answer1Q1;

    @Column(nullable = false)
    @Comment("Second answer choice for question 1")
    private String answer2Q1;

    @Column(nullable = false)
    @Comment("Third answer choice for question 1")
    private String answer3Q1;

    @Column(nullable = false)
    @Comment("Fourth answer choice for question 1")
    private String answer4Q1;

    @Column(nullable = false)
    @Comment("First answer choice for question 2")
    private String answer1Q2;

    @Column(nullable = false)
    @Comment("Second answer choice for question 2")
    private String answer2Q2;

    @Column(nullable = false)
    @Comment("Third answer choice for question 2")
    private String answer3Q2;

    @Column(nullable = false)
    @Comment("Fourth answer choice for question 2")
    private String answer4Q2;

    @Column(nullable = false)
    @Comment("First answer choice for question 3")
    private String answer1Q3;

    @Column(nullable = false)
    @Comment("Second answer choice for question 3")
    private String answer2Q3;

    @Column(nullable = false)
    @Comment("Third answer choice for question 3")
    private String answer3Q3;

    @Column(nullable = false)
    @Comment("Fourth answer choice for question 3")
    private String answer4Q3;

    @Column(nullable = false)
    @Comment("First answer choice for question 4")
    private String answer1Q4;

    @Column(nullable = false)
    @Comment("Second answer choice for question 4")
    private String answer2Q4;

    @Column(nullable = false)
    @Comment("Third answer choice for question 4")
    private String answer3Q4;

    @Column(nullable = false)
    @Comment("Fourth answer choice for question 4")
    private String answer4Q4;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Correct answer for question 4")
    private AnswerEnum correctAnswer4;
}

