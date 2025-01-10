package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Entity;

@Entity
public class PreparationMatchWordSentences extends AbstractBaseEntity {
    private String sentence;
    private String word;
}
