package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToeicPart3_4 extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitToeic_id")
    @Comment("Reference to the TOEIC submission this set of answers belongs to")
    private SubmitToeic submitToeic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeicPart3_4_id")
    @Comment("Reference to the TOEIC Part 3 or 4 question set")
    private ToeicPart3_4 toeicPart3_4;

    @Enumerated(EnumType.STRING)
    @Comment("Answer selected for question 1")
    private AnswerEnum answerQ1;

    @Enumerated(EnumType.STRING)
    @Comment("Answer selected for question 2")
    private AnswerEnum answerQ2;

    @Enumerated(EnumType.STRING)
    @Comment("Answer selected for question 3")
    private AnswerEnum answerQ3;
}

