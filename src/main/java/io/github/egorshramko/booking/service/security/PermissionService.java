package io.github.egorshramko.booking.service.security;

import io.github.egorshramko.booking.model.security.Permission;

import java.util.Set;

public interface PermissionService {

    Permission getByName(String name);
    Set<Permission> getAllPermissions();

}
