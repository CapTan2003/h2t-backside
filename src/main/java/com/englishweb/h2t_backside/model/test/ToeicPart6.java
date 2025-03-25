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

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart6 extends AbstractBaseEntity {

    @Column(nullable = false)
    @NotNull(message = "file cannot be null")
    private String file; // Luu docx file

    @Column(nullable = false)
    @NotNull(message = "contentQuestion1 cannot be null")
    private String contentQuestion1;

    @Column(nullable = false)
    @NotNull(message = "contentQuestion2 cannot be null")
    private String contentQuestion2;

    @Column(nullable = false)
    @NotNull(message = "contentQuestion3 cannot be null")
    private String contentQuestion3;

    @Column(nullable = false)
    @NotNull(message = "contentQuestion4 cannot be null")
    private String contentQuestion4;

    @Column(nullable = false)
    @NotNull(message = "answer1Q1 cannot be null")
    private String answer1Q1;

    @Column(nullable = false)
    @NotNull(message = "answer2Q1 cannot be null")
    private String answer2Q1;

    @Column(nullable = false)
    @NotNull(message = "answer3Q1 cannot be null")
    private String answer3Q1;

    @Column(nullable = false)
    @NotNull(message = "answer4Q1 cannot be null")
    private String answer4Q1;

    @Column(nullable = false)
    @NotNull(message = "answer1Q2 cannot be null")
    private String answer1Q2;

    @Column(nullable = false)
    @NotNull(message = "answer2Q2 cannot be null")
    private String answer2Q2;

    @Column(nullable = false)
    @NotNull(message = "answer3Q2 cannot be null")
    private String answer3Q2;

    @Column(nullable = false)
    @NotNull(message = "answer4Q2 cannot be null")
    private String answer4Q2;

    @Column(nullable = false)
    @NotNull(message = "answer1Q3 cannot be null")
    private String answer1Q3;

    @Column(nullable = false)
    @NotNull(message = "answer2Q3 cannot be null")
    private String answer2Q3;

    @Column(nullable = false)
    @NotNull(message = "answer3Q3 cannot be null")
    private String answer3Q3;

    @Column(nullable = false)
    @NotNull(message = "answer4Q3 cannot be null")
    private String answer4Q3;

    @Column(nullable = false)
    @NotNull(message = "answer1Q4 cannot be null")
    private String answer1Q4;

    @Column(nullable = false)
    @NotNull(message = "answer2Q4 cannot be null")
    private String answer2Q4;

    @Column(nullable = false)
    @NotNull(message = "answer3Q4 cannot be null")
    private String answer3Q4;

    @Column(nullable = false)
    @NotNull(message = "answer4Q4 cannot be null")
    private String answer4Q4;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "correctAnswer1 cannot be null")
    private AnswerEnum correctAnswer1;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "correctAnswer2 cannot be null")
    private AnswerEnum correctAnswer2;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "correctAnswer3 cannot be null")
    private AnswerEnum correctAnswer3;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "correctAnswer4 cannot be null")
    private AnswerEnum correctAnswer4;
}
