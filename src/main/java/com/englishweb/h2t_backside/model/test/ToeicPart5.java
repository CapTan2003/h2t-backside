package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart5 extends AbstractBaseEntity {

    @Column(nullable = false)
    @NotNull(message = "content cannot be null")
    private String content;

    @Column(nullable = false)
    @NotNull(message = "answer1 cannot be null")
    private String answer1;

    @Column(nullable = false)
    @NotNull(message = "answer2 cannot be null")
    private String answer2;

    @Column(nullable = false)
    @NotNull(message = "answer3 cannot be null")
    private String answer3;

    @Column(nullable = false)
    @NotNull(message = "answer4 cannot be null")
    private String answer4;

    @Column(nullable = false)
    @NotNull(message = "correctAnswer cannot be null")
    private AnswerEnum correctAnswer;
}
