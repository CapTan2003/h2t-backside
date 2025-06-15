package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.interfacemodel.AIResponseEntity;
import org.springframework.data.jpa.domain.Specification;

public class AIResponseSpecification {
    public static <T extends AIResponseEntity> Specification<T> findByUserId(Long id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), id);
    }

    public static <T extends AIResponseEntity> Specification<T> findByStatus(Boolean status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction(); // Return all if null
            }

            if (status) {
                // status = true -> Evaluated (có evaluate và không rỗng)
                return criteriaBuilder.and(
                        criteriaBuilder.isNotNull(root.get("evaluate")),
                        criteriaBuilder.notEqual(root.get("evaluate"), "")
                );
            } else {
                // status = false -> Not Evaluated (evaluate null hoặc rỗng)
                return criteriaBuilder.or(
                        criteriaBuilder.isNull(root.get("evaluate")),
                        criteriaBuilder.equal(root.get("evaluate"), "")
                );
            }
        };
    }
}


