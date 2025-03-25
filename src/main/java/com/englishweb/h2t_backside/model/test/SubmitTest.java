package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.User;
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
public class SubmitTest extends AbstractBaseEntity {
    @Column(nullable = false)
    @NotNull(message = "score cannot be null")
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    @Lob
    @NotNull(message = "comment cannot be null")
    private String comment;
}