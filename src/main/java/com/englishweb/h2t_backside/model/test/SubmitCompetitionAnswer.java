package com.englishweb.h2t_backside.model.test;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class SubmitCompetitionAnswer {
    @ManyToOne
    @JoinColumn(name = "submit_competition_id")
    private SubmitCompetition submitCompetition;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;
}
