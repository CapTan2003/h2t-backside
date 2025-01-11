package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart1 extends AbstractBaseEntity {
    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String audio;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private AnswerEnum correctAnswer;
}
