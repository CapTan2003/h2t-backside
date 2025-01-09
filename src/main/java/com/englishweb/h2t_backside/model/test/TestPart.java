package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.CompetitionEnum;
import jakarta.persistence.Entity;

@Entity
public class TestPart extends AbstractBaseEntity {
    private String questions;
    private CompetitionEnum type;
}