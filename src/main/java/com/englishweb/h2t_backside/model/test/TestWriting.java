package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestWriting extends AbstractBaseEntity {

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private int maxWords = 200;

    @Column(nullable = false)
    private int minWords = 20;
}
