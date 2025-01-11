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
public class SubmitToeicPart1 extends AbstractBaseEntity {
    @ManyToOne
    private SubmitToeic submitToeic;

    @ManyToOne
    private ToeicPart1 toeicPart1;

    @Enumerated(EnumType.STRING)
    private AnswerEnum answer;
}
