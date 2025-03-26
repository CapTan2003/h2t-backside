package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart5 extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Question content for TOEIC Part 5")
    private String content;

    @Column(nullable = false)
    @Comment("First answer choice")
    private String answer1;

    @Column(nullable = false)
    @Comment("Second answer choice")
    private String answer2;

    @Column(nullable = false)
    @Comment("Third answer choice")
    private String answer3;

    @Column(nullable = false)
    @Comment("Fourth answer choice")
    private String answer4;

    @Column(nullable = false)
    @Comment("Correct answer for the question")
    private AnswerEnum correctAnswer;
}

