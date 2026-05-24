package io.github.egorshramko.booking.facade.impl;

import io.github.egorshramko.booking.dto.PermissionDto;
import io.github.egorshramko.booking.facade.PermissionServiceFacade;
import io.github.egorshramko.booking.service.security.PermissionService;
import io.github.egorshramko.booking.utils.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class PermissionServiceFacadeImpl implements PermissionServiceFacade {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Set<PermissionDto> getAllSystemPermissions() {
        return permissionMapper.toDtoSet(permissionService.getAllPermissions());
    }

}
