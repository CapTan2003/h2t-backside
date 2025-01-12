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
public class TestListening extends AbstractBaseEntity {

    @Column(nullable = false)
    private String audio; // Luu duong dan file am thanh cua Reading

    @Column(nullable = false)
    private String transcript;

    @Column(nullable = false)
    @ColumnDefault("''")
    private String questions; // Luu danh sach id cac cau hoi
}
