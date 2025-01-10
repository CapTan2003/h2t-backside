package com.englishweb.h2t_backside.model.test;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

public class SubmitTestWriting {
    @ManyToOne
    @JoinColumn(name = "submit_test_id")
    private SubmitTest submitTest;

    @ManyToOne
    @JoinColumn(name = "writing_id")
    private TestWriting writing;

    @Lob
    private String content;
    private Integer score;
}