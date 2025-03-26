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
public class SubmitToeicPart7 extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitToeic_id")
    @Comment("Reference to the TOEIC submission this answer belongs to")
    private SubmitToeic submitToeic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeicPart7Question_id")
    @Comment("Reference to the TOEIC Part 7 question")
    private ToeicPart7Question toeicPart7Question;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Answer selected by the user")
    private AnswerEnum answer;
}

