package com.englishweb.h2t_backside.repository;

import com.englishweb.h2t_backside.model.RouteNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteNodeRepository extends JpaRepository<RouteNode, Long> {
    List<RouteNode> findByRoute_Id(Long routeId);
}
