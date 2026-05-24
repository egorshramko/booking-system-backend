package io.github.egorshramko.booking.facade.impl;

import io.github.egorshramko.booking.dto.PermissionDto;
import io.github.egorshramko.booking.dto.RoleDto;
import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.facade.RoleServiceFacade;
import io.github.egorshramko.booking.model.security.Role;
import io.github.egorshramko.booking.service.security.PermissionService;
import io.github.egorshramko.booking.service.security.RoleService;
import io.github.egorshramko.booking.utils.mapper.PermissionMapper;
import io.github.egorshramko.booking.utils.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RoleServiceFacadeImpl implements RoleServiceFacade {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public RoleDto addRole(RoleDto roleDto) throws EmptyRequiredFieldException {
        Role convertedRole = roleMapper.toEntity(roleDto);
        Role addedRole = roleService.addRole(convertedRole);
        return roleMapper.toDto(addedRole);
    }

    @Override
    public RoleDto getRoleById(Long roleId) {
        return roleMapper.toDto(roleService.getRoleById(roleId));
    }

    @Override
    public RoleDto editRole(Long roleId, RoleDto roleDto) throws EmptyRequiredFieldException {

        Role convertedRole = roleMapper.toEntity(roleDto);
        convertedRole.setId(roleId);

        Role editedRole = roleService.editRole(convertedRole);

        return roleMapper.toDto(editedRole);
    }

    @Override
    public void removeRole(Long roleId) {
        roleService.removeRole(roleId);
    }

    @Override
    public Set<RoleDto> getRolePage(Integer pageNumber) {
        Page<Role> rolesPage = roleService.getRolePage(pageNumber);
        Set<Role> roleSet = rolesPage.get().collect(Collectors.toSet());
        return roleMapper.toDtoSet(roleSet);
    }
}
