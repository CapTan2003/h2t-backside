package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
