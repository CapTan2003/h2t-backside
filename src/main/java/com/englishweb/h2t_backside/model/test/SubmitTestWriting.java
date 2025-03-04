package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitTestWriting extends AbstractBaseEntity {
    @ManyToOne
    @JoinColumn(name = "submit_test_id")
    private SubmitTest submitTest;

    @ManyToOne
    @JoinColumn(name = "writing_id")
    private TestWriting writing;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer score;

    @Lob
    private String comment;
}