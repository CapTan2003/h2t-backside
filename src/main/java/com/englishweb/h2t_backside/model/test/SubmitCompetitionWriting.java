package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
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
public class SubmitCompetitionWriting extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_competition_id")
    @Comment("Reference to the related competition submission")
    private SubmitCompetition submitCompetition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writing_id")
    @Comment("Reference to the writing test question")
    private TestWriting writing;

    @Lob
    @Column(nullable = false)
    @Comment("Content written by the user for the writing test")
    private String content;

    @Column(nullable = false)
    @Comment("Score given for the writing submission")
    private Integer score;
}