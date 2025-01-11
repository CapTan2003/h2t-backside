package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reading extends AbstractLessonEntity {

    @Column(nullable = false)
    @Comment("URL of the DOCX file stored in Firebase associated with the reading lesson")
    private String file;

    @Comment("Questions related to the Reading lesson")
    @ColumnDefault("''")
    private String questions = "";

    @OneToOne
    @JoinColumn(name = "preparation_id")
    @Comment("Preparation object associated with this reading lesson")
    private Preparation preparation;
}
