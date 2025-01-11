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
public class SubmitToeicPart2 extends AbstractBaseEntity {
    @ManyToOne
    private SubmitToeic submitToeic;

    @ManyToOne
    private ToeicPart2 toeicPart2;

    @Enumerated(EnumType.STRING)
    private AnswerEnum answer;
}
