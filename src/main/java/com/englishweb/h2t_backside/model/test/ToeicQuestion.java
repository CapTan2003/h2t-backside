package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.Comment;

import java.util.List;

public class ToeicQuestion extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Content of the question")
    private String content;

    @NotBlank(message = "Explanation cannot be empty")
    private String explanation;

    @OneToMany(mappedBy = "toeicQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ToeicAnswer> toeicAnswers;
}
