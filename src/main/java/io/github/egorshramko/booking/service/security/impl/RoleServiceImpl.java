package io.github.egorshramko.booking.service.security.impl;

import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.exception.RoleUniqueException;
import io.github.egorshramko.booking.exception.SystemRoleModificationException;
import io.github.egorshramko.booking.model.security.Permission;
import io.github.egorshramko.booking.model.security.Role;
import io.github.egorshramko.booking.model.security.RoleType;
import io.github.egorshramko.booking.repository.security.PermissionRepository;
import io.github.egorshramko.booking.repository.security.RoleRepository;
import io.github.egorshramko.booking.service.security.PermissionService;
import io.github.egorshramko.booking.service.security.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public Role addRole(Role role) throws EmptyRequiredFieldException {

        log.info("Start adding role");
        log.debug("role: {}", role);
        if (role.getName() == null) {
            log.warn("Role name is empty");
            throw new EmptyRequiredFieldException("Role name is empty");
        }

        //Загрузка разрешений из базы и привязка к создаваемой роли
        final Set<Permission> permissionsEntitySet = role.getPermissions().stream()
                        .map((permission) ->
                                permissionRepository.getByTypeAndObject(
                                        permission.getType(),
                                        permission.getObject())
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                "Unknown permission " + permission.getAuthority())))
                                .collect(Collectors.toSet());
        role.setPermissions(permissionsEntitySet);

        log.debug("Role after loading permissions: {}", role);
        log.info("Searching role with name {}", role.getName());
        //Поиск роли с аналогичным названием
        final Optional<Role> roleOptional = roleRepository.findByName(role.getName());
        if (roleOptional.isEmpty()) {
            log.info("Role not found");
            role.setActual(true);
            role.setType(RoleType.CUSTOM);
            return roleRepository.save(role);
        }
        else {
            final Role roleEntity = roleOptional.get();
            if (roleEntity.isActual()) {
                log.warn("Role found");
                throw new RoleUniqueException("Role with name " + role.getName() + " is already exists");
            }
            else {
                log.info("Found not actual role");
                roleEntity.setActual(true);
                roleEntity.setType(RoleType.CUSTOM);
                roleEntity.setPermissions(role.getPermissions());
                return roleRepository.save(roleEntity);
            }
        }

    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role with id " + roleId + " not found"));
    }

    @Override
    @Transactional
    public Role editRole(Role role) throws EmptyRequiredFieldException {

        log.info("Start edit role");
        if (role.getId() == null) {
            throw new EmptyRequiredFieldException("Empty id in role entity");
        }

        log.info("Searching entity for editing");
        final Role roleEntity = roleRepository.findByIdAndActualIsTrue(role.getId())
                .orElseThrow(() -> new EntityNotFoundException("Role with id " + role.getId() + " not found"));
        if (roleEntity.getType() != RoleType.SYSTEM) {
            roleEntity.setName(role.getName());

            log.debug("Input role: {}", role);
            //Обновление разрешений
            final Set<Permission> permissionsEntitySet = role.getPermissions().stream()
                    .map((permission) ->
                            permissionRepository.getByTypeAndObject(
                                            permission.getType(),
                                            permission.getObject())
                                    .orElseThrow(() -> new EntityNotFoundException(
                                            "Unknown permission " + permission.getAuthority())))
                    .collect(Collectors.toSet());
            roleEntity.setPermissions(permissionsEntitySet);

            return roleRepository.save(roleEntity);
        }
        else {
            throw new SystemRoleModificationException("Attempt to edit system role");
        }


    }

    @Override
    @Transactional
    public void removeRole(Long roleId) {

        log.info("Start removing role with id {}", roleId);
        Optional<Role> roleOptional = roleRepository.findByIdAndActualIsTrue(roleId);
        if (roleOptional.isPresent()) {
            log.info("Role for removing found");

            Role removingRole = roleOptional.get();
            if (removingRole.getType() != RoleType.SYSTEM) {
                removingRole.setActual(false);
                roleRepository.save(removingRole);
            }
            else {
                throw new SystemRoleModificationException("Attempt to removing system role");
            }

        }
        else {
            log.info("Role for removing not found");
        }
    }

    @Override
    public Page<Role> getRolePage(Integer pageNumber) {
        return roleRepository.findAllByActualIsTrue(PageRequest.of(pageNumber, 20, Sort.by("name").ascending()));
    }
}
