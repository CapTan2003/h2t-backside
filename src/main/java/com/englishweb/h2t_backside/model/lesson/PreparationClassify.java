package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class PreparationClassify extends AbstractBaseEntity {
    private String group;
    private String members;
}
