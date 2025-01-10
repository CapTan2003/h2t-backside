package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractLessonEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeakingConversation extends AbstractLessonEntity {
    private String name;
    private int serial;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaking_id")
    private Speaking speaking;
}
