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
public class SubmitToeicPart7 extends AbstractBaseEntity {

    @ManyToOne
    private SubmitToeic submitToeic;

    @ManyToOne
    private ToeicPart7Question toeicPart7Question;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnswerEnum answer;
}
