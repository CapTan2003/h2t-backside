package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
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
public class LessonQuestion extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Content of the question")
    private String content;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Comment("Explanation of the question")
    private String explanation;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonAnswer> answers;
}
