package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Speaking extends AbstractLessonEntity {

    @Column(nullable = false)
    @Comment("Topic of the speaking lesson")
    private String topic;

    @Column(nullable = false)
    @Comment("Duration of the speaking lesson in seconds")
    private Integer duration;

    @OneToOne
    @JoinColumn(name = "preparation_id")
    @Comment("Preparation object associated with this speaking lesson")
    private Preparation preparation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "speaking_id")
    @Comment("List of conversations related to the speaking lesson")
    private List<SpeakingConversation> conversations;
}
