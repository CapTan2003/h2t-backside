package com.englishweb.h2t_backside.mapper;

import com.englishweb.h2t_backside.dto.RouteNodeDTO;
import com.englishweb.h2t_backside.model.Route;
import com.englishweb.h2t_backside.model.RouteNode;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RouteNodeMapper {

    // Convert DTO -> Entity
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    @Mapping(target = "nodeId", source = "dto.nodeId")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "route", source = "dto.routeId", qualifiedByName = "mapRouteIdToRoute")
    RouteNode convertToEntity(RouteNodeDTO dto);

    // Convert Entity -> DTO
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    @Mapping(target = "nodeId", source = "entity.nodeId")
    @Mapping(target = "type", source = "entity.type")
    @Mapping(target = "routeId", source = "entity.route.id")
    RouteNodeDTO convertToDTO(RouteNode entity);

    // Cập nhật Entity từ DTO
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "nodeId", source = "dto.nodeId")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "route", source = "dto.routeId", qualifiedByName = "mapRouteIdToRoute")
    void patchEntityFromDTO(RouteNodeDTO dto, @MappingTarget RouteNode entity);

    // Custom mapping methods
    @Named("mapRouteIdToRoute")
    default Route mapRouteIdToRoute(Long routeId) {
        if (routeId == null) return null;
        Route route = new Route();
        route.setId(routeId);
        return route;
    }
}
