package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Speaking extends AbstractLessonEntity {
    private String topic;
    private Integer duration;

    @OneToOne
    @JoinColumn(name = "preparation_id")
    private Preparation preparation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<SpeakingConversation> conversations;
}
