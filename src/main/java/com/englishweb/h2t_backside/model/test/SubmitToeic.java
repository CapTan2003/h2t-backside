package com.englishweb.h2t_backside.model.test;

import com.englishweb.h2t_backside.model.User;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

public class SubmitToeic {
    private Toeic toeic;
    private User user;
    private LocalDateTime submitTime;
    private Integer score;
    @Lob
    private String comment;
}
