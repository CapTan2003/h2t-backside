package com.englishweb.h2t_backside.repository.test;

import com.englishweb.h2t_backside.model.test.SubmitCompetition;
import com.englishweb.h2t_backside.model.test.SubmitTest;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubmitCompetitionRepository extends JpaRepository<SubmitCompetition, Long>, JpaSpecificationExecutor<SubmitCompetition> {
    int countByUserIdAndStatusTrue(Long userId);

    @Query("SELECT COALESCE(SUM(s.score), 0) FROM SubmitCompetition s WHERE s.user.id = :userId AND s.status = true")
    double sumScoreByUserIdAndStatusTrue(@Param("userId") Long userId);

    Optional<SubmitCompetition> findByTestIdAndUserIdAndStatusFalse(Long testId, Long userId);

    List<SubmitCompetition> findByUserId(Long userId);

    List<SubmitCompetition> findAllByUserId(Long userId);
    List<SubmitCompetition> findAllByUserIdAndStatus(Long userId, Boolean status);
}
