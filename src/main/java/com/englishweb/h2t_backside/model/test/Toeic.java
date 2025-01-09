package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Entity;

@Entity
public class Toeic extends AbstractBaseEntity {
    private String title;
    private int duration = 120;
    private String questionsPart1;
    private String questionsPart2;
    private String questionsPart3_4;
    private String questionsPart5;
    private String questionsPart6;
    private String questionsPart7;
}