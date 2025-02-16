package com.englishweb.h2t_backside.mapper;


import com.englishweb.h2t_backside.dto.UserDTO;
import com.englishweb.h2t_backside.model.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = RouteNodeMapper.class,
        builder = @Builder(disableBuilder = true)
)
public interface UserMapper {

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "avatar", source = "dto.avatar")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "password", source = "dto.password")
    @Mapping(target = "roleEnum", source = "dto.roleEnum")
    @Mapping(target = "levelEnum", source = "dto.levelEnum")
    @Mapping(target = "phoneNumber", source = "dto.phoneNumber")
    @Mapping(target = "dateOfBirth", source = "dto.dateOfBirth")
    @Mapping(target = "status", source = "dto.status", defaultValue = "ACTIVE")
    User convertToEntity(UserDTO dto);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "avatar", source = "entity.avatar")
    @Mapping(target = "email", source = "entity.email")
    @Mapping(target = "roleEnum", source = "entity.roleEnum")
    @Mapping(target = "levelEnum", source = "entity.levelEnum")
    @Mapping(target = "phoneNumber", source = "entity.phoneNumber")
    @Mapping(target = "dateOfBirth", source = "entity.dateOfBirth")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    UserDTO convertToDTO(User entity);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "avatar", source = "dto.avatar")
    @Mapping(target = "password", source = "dto.password")
    @Mapping(target = "roleEnum", source = "dto.roleEnum")
    @Mapping(target = "levelEnum", source = "dto.levelEnum")
    @Mapping(target = "phoneNumber", source = "dto.phoneNumber")
    @Mapping(target = "dateOfBirth", source = "dto.dateOfBirth")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "email", ignore = true) //Bo qua truong email khi update
    void patchEntityFromDTO(UserDTO dto, @MappingTarget User entity);
}

