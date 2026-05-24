package io.github.egorshramko.booking.utils.mapper;

import io.github.egorshramko.booking.dto.RoleDto;
import io.github.egorshramko.booking.model.security.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = PermissionMapper.class)
public interface RoleMapper {

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);

    Set<RoleDto> toDtoSet(Set<Role> roleSet);

    Set<Role> toEntitySet(Set<RoleDto> dtoSet);

}
