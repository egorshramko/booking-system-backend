package io.github.egorshramko.booking.utils.mapper;

import io.github.egorshramko.booking.dto.RoleDto;
import io.github.egorshramko.booking.model.security.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = PermissionMapper.class)
public interface RoleMapper {

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);

    Set<RoleDto> toDtoSet(Set<Role> roleSet);

    Set<Role> toEntitySet(Set<RoleDto> dtoSet);

    default Set<RoleDto> mapRoleNamesToRoleDto(Set<String> roles) {
        return roles.stream()
                .map(roleName -> new RoleDto(null, roleName, null))
                .collect(Collectors.toSet());

    }

    default Set<String> mapRoleDtosToRoleNames(Set<RoleDto> roleDtos) {
        return roleDtos.stream()
                .map(RoleDto::name)
                .collect(Collectors.toSet());
    }

}
