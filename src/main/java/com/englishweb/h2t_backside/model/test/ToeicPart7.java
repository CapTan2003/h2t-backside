package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart7 extends AbstractBaseEntity {

    @Column(nullable = false)
    @NotNull(message = "file cannot be null")
    private String file; // Luu docx file

    @ColumnDefault("''")
    @NotNull(message = "questions cannot be null")
    private String questions; // Luu danh sach id cau hoi
}
