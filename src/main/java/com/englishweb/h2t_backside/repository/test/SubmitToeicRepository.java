package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitTest;
import com.englishweb.h2t_backside.model.test.SubmitToeic;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubmitToeicRepository extends JpaRepository<SubmitToeic, Long>, JpaSpecificationExecutor<SubmitToeic> {
    int countByUserIdAndStatusTrue(Long userId);

    @Query("SELECT COALESCE(SUM(s.score), 0) FROM SubmitToeic s WHERE s.user.id = :userId AND s.status = true")
    double sumScoreByUserIdAndStatusTrue(@Param("userId") Long userId);

    List<SubmitToeic> findByUserIdAndStatusTrue(Long userId);

    List<SubmitToeic> findAllByUserId(Long userId);
    List<SubmitToeic> findAllByUserIdAndStatus(Long userId, Boolean status);

}
