package com.englishweb.h2t_backside.repository.specifications;

import com.englishweb.h2t_backside.model.interfacemodel.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static <T extends UserEntity> Specification<T> findByName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%" + name.toLowerCase() + "%");
    }

    public static <T extends UserEntity> Specification<T> findByEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("email")), email.toLowerCase() );
    }

}

