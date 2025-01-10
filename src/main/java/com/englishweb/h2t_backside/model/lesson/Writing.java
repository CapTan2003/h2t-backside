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
public class Writing extends AbstractLessonEntity {
    private String topic;
    private String file; // Luu file cho nguoi dung doc ve chu de
    @Lob
    private String tips; // Exmaple data: "["Tip 1", "Tip 2", "Tip 3"]"
    @Lob
    private String paragraph;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WritingAnswer> answers;

    @OneToOne
    @JoinColumn(name = "preparation_id")
    private Preparation preparation;
}
