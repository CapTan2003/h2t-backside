package com.englishweb.h2t_backside.model;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Entity;

@Entity
public class ToeicPart1 extends AbstractBaseEntity {
    private String image;
    private String audio;
    private AnswerEnum correctAnswer;
}
