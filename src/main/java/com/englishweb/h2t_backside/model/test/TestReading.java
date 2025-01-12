package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestReading extends AbstractBaseEntity {

    @Column(nullable = false)
    private String file; // Luu duong dan file docx cua Reading

    @Column(nullable = false)
    @ColumnDefault("''")
    private String questions; // Luu danh sach id cac cau hoi
}
