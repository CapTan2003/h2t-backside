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
public class SubmitTestWriting extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_test_id")
    @Comment("Reference to the related test submission")
    private SubmitTest submitTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writing_id")
    @Comment("Reference to the writing test question")
    private TestWriting writing;

    @Lob
    @Column(nullable = false)
    @Comment("Written content submitted by the user")
    private String content;

    @Column(nullable = false)
    @Comment("Score given for the writing")
    private Integer score;

    @Lob
    @Comment("Comment provided by the reviewer or system")
    private String comment;
}
