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
public class SubmitToeicPart5 extends AbstractBaseEntity {

    @ManyToOne
    private SubmitToeic submitToeic;

    @ManyToOne
    private ToeicPart5 toeicPart5;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerEnum answer;
}
