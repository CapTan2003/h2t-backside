package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Entity;

@Entity
public class ToeicPart6 extends AbstractBaseEntity {
    private String file; // Luu docx file
    private String contentQuestion1;
    private String contentQuestion2;
    private String contentQuestion3;
    private String contentQuestion4;
    private String answer1Q1;
    private String answer2Q1;
    private String answer3Q1;
    private String answer4Q1;
    private String answer1Q2;
    private String answer2Q2;
    private String answer3Q2;
    private String answer4Q2;
    private String answer1Q3;
    private String answer2Q3;
    private String answer3Q3;
    private String answer4Q3;
    private String answer1Q4;
    private String answer2Q4;
    private String answer3Q4;
    private String answer4Q4;
    private AnswerEnum correctAnswer1;
    private AnswerEnum correctAnswer2;
    private AnswerEnum correctAnswer3;
    private AnswerEnum correctAnswer4;
}
