package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.PreparationEnum;
import jakarta.persistence.Entity;

@Entity
public class Preparation extends AbstractBaseEntity {
    private String title;
    private String tip;
    private String questions;
    private PreparationEnum type;
}

/*
* type:
*  - CLASSIFY: questions luu ds id cua preparationClassify
*  - WORDS_MAKE_SENTENCES: questions luu ds id cua preparationMakeSentences
*  - MATCH_WORD_WITH_SENTENCES:
* */