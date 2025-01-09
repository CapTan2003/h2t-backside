package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Entity;

@Entity
public class ToeicPart7Question extends AbstractBaseEntity {
    private String content;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private AnswerEnum correctAnswer;
}
