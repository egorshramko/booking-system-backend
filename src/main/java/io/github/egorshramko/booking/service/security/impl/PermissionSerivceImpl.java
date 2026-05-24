package io.github.egorshramko.booking.service.security.impl;

import io.github.egorshramko.booking.model.security.ObjectType;
import io.github.egorshramko.booking.model.security.Permission;
import io.github.egorshramko.booking.model.security.PermissionType;
import io.github.egorshramko.booking.repository.security.PermissionRepository;
import io.github.egorshramko.booking.service.security.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PermissionSerivceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission getByName(String name) {

        String[] nameParts = name.split("_", 2);
        PermissionType type = PermissionType.valueOf(nameParts[0]);
        ObjectType object = ObjectType.valueOf(nameParts[1]);

        return permissionRepository.getByTypeAndObject(type, object)
                .orElse(null);
    }

    @Override
    public Set<Permission> getAllPermissions() {
        return new HashSet<>(permissionRepository.findAll());
    }
}
