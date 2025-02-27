package com.englishweb.h2t_backside.repository;

import com.englishweb.h2t_backside.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findAllByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
}
