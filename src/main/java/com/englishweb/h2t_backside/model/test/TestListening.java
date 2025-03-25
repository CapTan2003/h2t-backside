package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "audio cannot be null")
    private String audio;

    @Column(nullable = false)
    @NotNull(message = "transcript cannot be null")
    private String transcript;

    @Column(nullable = false)
    @ColumnDefault("''")
    @NotNull(message = "questions cannot be null")
    private String questions; // Luu danh sach id cac cau hoi
}
