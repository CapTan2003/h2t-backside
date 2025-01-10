package com.englishweb.h2t_backside.model.test;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class SubmitTestSpeaking {
    @ManyToOne
    @JoinColumn(name = "submit_test_id")
    private SubmitTest submitTest;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String transcript;
    private Integer score;
}