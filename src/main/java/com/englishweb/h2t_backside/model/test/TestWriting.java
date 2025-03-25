package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TestWriting extends AbstractBaseEntity {

    @Column(nullable = false)
    @NotNull(message = "topic cannot be null")
    private String topic;

    @Column(nullable = false)
    @Builder.Default
    private int maxWords = 200;

    @Column(nullable = false)
    @Builder.Default
    private int minWords = 20;
}
