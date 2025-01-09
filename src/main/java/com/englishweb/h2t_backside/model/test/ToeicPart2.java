package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Entity;

@Entity
public class ToeicPart2 extends AbstractBaseEntity {
    private String audio;
    private AnswerEnum correctAnswer;
}
