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

    @Comment("Audio file URL or path associated with the lesson")
    private String audio;

    @Lob
    @Comment("Transcript of the audio, stored as a DOCX file")
    private String transcript;

    @Comment("Questions related to the Listening section")
    private String questions = "";

    @OneToOne
    @JoinColumn(name = "preparation_id", nullable = false)
    @Comment("Preparation object associated with this listening lesson")
    private Preparation preparation;
}
