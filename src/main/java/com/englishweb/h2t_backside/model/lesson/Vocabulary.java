package com.englishweb.h2t_backside.model.lesson;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import com.englishweb.h2t_backside.model.enummodel.WordTypeEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
public class Vocabulary extends AbstractBaseEntity {
    @Lob
    private String example;
    private String image;
    private String word;
    private String meaning;
    private String phonetic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("Type of the word (e.g., Noun, Verb, etc.)")
    private WordTypeEnum wordType = WordTypeEnum.NOUN;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topic topic;
}
