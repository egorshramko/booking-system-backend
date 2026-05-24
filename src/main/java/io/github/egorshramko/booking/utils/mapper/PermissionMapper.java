package io.github.egorshramko.booking.utils.mapper;

import io.github.egorshramko.booking.dto.PermissionDto;
import io.github.egorshramko.booking.model.security.ObjectType;
import io.github.egorshramko.booking.model.security.Permission;
import io.github.egorshramko.booking.model.security.PermissionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper {

    @Mapping(target = "name", expression = "java(permission.getAuthority())")
    PermissionDto toDto(Permission permission);

    @Mapping(target = "actual", constant = "true")
    @Mapping(target = "type", source = "name", qualifiedByName = "mapType")
    @Mapping(target = "object", source = "name", qualifiedByName = "mapObject")
    Permission toEntity(PermissionDto permissionDto);

    @Named("mapType")
    default String mapType(String name) {
        return name.split("_", 2)[0];
    }

    @Named("mapObject")
    default String mapObject(String name) {
        return name.split("_", 2)[1];
    }

}
