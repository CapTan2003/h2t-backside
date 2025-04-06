package com.englishweb.h2t_backside.model.features;

import com.englishweb.h2t_backside.model.User;
import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIResponse extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Prompt send to AI")
    private String request;

    @Column(nullable = false)
    @Comment("Response from AI")
    private String response;

    @Comment("Evaluate of real teacher")
    private String evaluate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
