package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToeicPart6 extends AbstractBaseEntity {

    @ManyToOne
    private SubmitToeic submitToeic;

    @ManyToOne
    private ToeicPart6 toeicPart6;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerEnum answerQ1;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerEnum answerQ2;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerEnum answerQ3;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerEnum answerQ4;
}
