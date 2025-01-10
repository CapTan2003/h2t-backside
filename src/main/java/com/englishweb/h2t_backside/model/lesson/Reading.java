package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reading extends AbstractLessonEntity {
    private String file;
    @Comment("Questions related to the Reading")
    private String questions = "";

    @OneToOne
    @JoinColumn(name = "preparation_id")
    private Preparation preparation;
}
