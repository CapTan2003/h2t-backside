package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.User;
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
public class SubmitTest extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Score achieved in the test")
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Comment("User who submitted the test")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    @Comment("Test that was submitted")
    private Test test;

    @Lob
    @Comment("Comment provided by the user or system")
    private String comment;
}