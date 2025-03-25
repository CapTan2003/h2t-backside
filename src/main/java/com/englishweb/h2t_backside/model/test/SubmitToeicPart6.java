package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToeicPart6 extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitToeic_id")
    private SubmitToeic submitToeic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeicPart6_id")
    private ToeicPart6 toeicPart6;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "answerQ1 cannot be null")
    private AnswerEnum answerQ1;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "answerQ2 cannot be null")
    private AnswerEnum answerQ2;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "answerQ3 cannot be null")
    private AnswerEnum answerQ3;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "answerQ4 cannot be null")
    private AnswerEnum answerQ4;
}
