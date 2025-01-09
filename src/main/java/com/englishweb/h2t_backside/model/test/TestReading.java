package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Entity;

@Entity
public class TestReading extends AbstractBaseEntity {
    private String file; // Luu duong dan file docx cua Reading
    private String questions; // Luu danh sach id cac cau hoi
}
