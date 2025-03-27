package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitTest;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubmitTestRepository extends JpaRepository<SubmitTest, Long> {

    int countByUserIdAndStatusTrue(Long userId);

    @Query("SELECT COALESCE(SUM(s.score), 0) FROM SubmitTest s WHERE s.user.id = :userId AND s.status = true")
    double sumScoreByUserIdAndStatusTrue(@Param("userId") Long userId);

    List<SubmitTest> findByUserIdAndStatusTrue(Long userId);

}
