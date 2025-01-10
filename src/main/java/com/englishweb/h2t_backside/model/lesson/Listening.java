package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Listening extends AbstractLessonEntity {
    private String audio;
    @Lob
    private String transcript;
    @Comment("Questions related to the Listening")
    private String questions = "";

    @OneToOne
    @JoinColumn(name = "preparation_id")
    private Preparation preparation;
}
