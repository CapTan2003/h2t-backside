package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.CompetitionEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
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
public class TestPart extends AbstractBaseEntity {

    @Column(nullable = false)
    @ColumnDefault("''")
    @NotNull(message = "questions cannot be null")
    private String questions;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "type cannot be null")
    private CompetitionEnum type;
}