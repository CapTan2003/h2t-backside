package com.englishweb.h2t_backside.repository;

import com.englishweb.h2t_backside.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
