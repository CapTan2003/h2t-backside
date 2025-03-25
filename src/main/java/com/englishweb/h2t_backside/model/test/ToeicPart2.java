package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart2 extends AbstractBaseEntity {
    @Column(nullable = false)
    @NotNull(message = "audio cannot be null")
    private String audio;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "correctAnswer cannot be null")
    private AnswerEnum correctAnswer;
}
