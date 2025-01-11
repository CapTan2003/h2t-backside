package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToeicPart3_4 extends AbstractBaseEntity {
    @ManyToOne
    private SubmitToeic submitToeic;

    @ManyToOne
    private ToeicPart3_4 toeicPart3_4;

    @Enumerated(EnumType.STRING)
    private AnswerEnum answerQ1;

    @Enumerated(EnumType.STRING)
    private AnswerEnum answerQ2;

    @Enumerated(EnumType.STRING)
    private AnswerEnum answerQ3;
}
