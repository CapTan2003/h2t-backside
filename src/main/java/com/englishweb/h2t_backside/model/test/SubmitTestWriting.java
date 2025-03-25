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
public class SubmitTestWriting extends AbstractBaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_test_id")
    private SubmitTest submitTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writing_id")
    private TestWriting writing;

    @Lob
    @Column(nullable = false)
    @NotNull(message = "content cannot be null")
    private String content;

    @Column(nullable = false)
    @NotNull(message = "score cannot be null")
    private Integer score;

    @Lob
    @NotNull(message = "comment cannot be null")
    private String comment;
}