package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.abstractmodel.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestListening extends AbstractBaseEntity {

    @Column(nullable = false)
    @Comment("Audio file path used for the listening test")
    private String audio;

    @Column(nullable = false)
    @Comment("Transcript of the listening audio")
    private String transcript;

    @Column(nullable = false)
    @ColumnDefault("''")
    @Comment("List of question IDs associated with this listening test")
    private String questions;
}

