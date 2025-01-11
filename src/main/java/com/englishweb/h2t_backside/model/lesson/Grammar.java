package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grammar extends AbstractLessonEntity {

    @Column(nullable = false)
    @Comment("Document file for grammar")
    private String file;

    @Column(nullable = false)
    @Comment("Definition of grammar")
    private String definition;

    @Column(nullable = false)
    @Comment("Example for grammar")
    private String example;

    @Comment("Questions related to the Grammar")
    @ColumnDefault("''")
    private String questions = "";
}
