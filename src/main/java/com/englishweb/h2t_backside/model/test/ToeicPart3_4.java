package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.AnswerEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToeicPart3_4 extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Audio file for the TOEIC Part 3/4 question")
    private String audio;

    @Column(nullable = false)
    @Comment("Image shown with the question set")
    private String image;

    @Column(nullable = false)
    @Comment("Transcript shown with the question set")
    private String transcript;
    @Builder.Default
    @Comment("List of question IDs of Toeic Part 3 and 4")
    private String questions = "";
}

