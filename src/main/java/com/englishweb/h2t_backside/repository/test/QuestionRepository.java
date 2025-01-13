package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
