package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitTestSpeaking extends AbstractBaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_test_id")
    private SubmitTest submitTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false)
    @ColumnDefault("''")
    @NotNull(message = "transcript cannot be null")
    private String transcript;

    @NotNull(message = "file cannot be null")
    private String file;

    @Column(nullable = false)
    @NotNull(message = "score cannot be null")
    private Integer score;

    @Lob
    @NotNull(message = "comment cannot be null")
    private String comment;
}