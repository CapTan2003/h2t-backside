package com.englishweb.h2t_backside.model;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Entity;

@Entity
public class ToeicPart7 extends AbstractBaseEntity {
    private String file; // Luu docx file
    private String questions; // Luu danh sach id cau hoi
}
