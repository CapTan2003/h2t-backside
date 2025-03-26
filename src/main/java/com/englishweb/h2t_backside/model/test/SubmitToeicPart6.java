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
public class SubmitToeicPart6 extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitToeic_id")
    @Comment("Reference to the TOEIC submission this answer set belongs to")
    private SubmitToeic submitToeic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeicPart6_id")
    @Comment("Reference to the TOEIC Part 6 question set")
    private ToeicPart6 toeicPart6;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Answer selected for question 1")
    private AnswerEnum answerQ1;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Answer selected for question 2")
    private AnswerEnum answerQ2;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Answer selected for question 3")
    private AnswerEnum answerQ3;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Answer selected for question 4")
    private AnswerEnum answerQ4;
}

