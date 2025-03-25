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
public class SubmitToeicPart3_4 extends AbstractBaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitToeic_id")
    private SubmitToeic submitToeic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeicPart3_4_id")
    private ToeicPart3_4 toeicPart3_4;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "answerQ1 cannot be null")
    private AnswerEnum answerQ1;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "answerQ2 cannot be null")
    private AnswerEnum answerQ2;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "answerQ3 cannot be null")
    private AnswerEnum answerQ3;
}
