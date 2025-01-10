package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class SubmitTest {
    private Integer score;
    private LocalDateTime submitTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @Lob
    private String comment;
}