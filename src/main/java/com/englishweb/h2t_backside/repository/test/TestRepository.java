package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
