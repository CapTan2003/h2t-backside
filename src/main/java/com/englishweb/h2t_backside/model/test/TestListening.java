package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Entity;

@Entity
public class TestListening extends AbstractBaseEntity {
    private String audio; // Luu duong dan file am thanh cua Reading
    private String transcript;
    private String questions; // Luu danh sach id cac cau hoi
}
