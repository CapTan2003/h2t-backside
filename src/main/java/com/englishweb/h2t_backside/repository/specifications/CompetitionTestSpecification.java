package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.test.CompetitionTest;
import org.springframework.data.jpa.domain.Specification;

public class CompetitionTestSpecification {
    public static Specification<CompetitionTest> findByName(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }
}
