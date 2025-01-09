package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Entity;

@Entity
public class TestWriting extends AbstractBaseEntity {
    private String topic;
    private int maxWords = 200;
    private int minWords = 20;
}
