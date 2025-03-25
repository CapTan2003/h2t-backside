package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitCompetitionWriting extends AbstractBaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_competition_id")
    private SubmitCompetition submitCompetition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writing_id")
    private TestWriting writing;

    @Lob
    @Column(nullable = false)
    @NotNull(message = "content cannot be null")
    private String content;

    @NotNull(message = "score cannot be null")
    @Column(nullable = false)
    private Integer score;
}