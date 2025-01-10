package com.englishweb.h2t_backside.model.test;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

public class SubmitCompetitionWriting {
    @ManyToOne
    @JoinColumn(name = "submit_competition_id")
    private SubmitCompetition submitCompetition;

    @ManyToOne
    @JoinColumn(name = "writing_id")
    private TestWriting writing;

    @Lob
    private String content;
    private Integer score;
}