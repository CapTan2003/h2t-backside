package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grammar extends AbstractLessonEntity {
    private String file;
    private String definition;
    private String example;
    @Comment("Questions related to the Grammar")
    private String questions = "";
}
