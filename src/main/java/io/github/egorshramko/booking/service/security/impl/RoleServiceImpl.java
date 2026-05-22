package io.github.egorshramko.booking.service.security.impl;

import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.exception.RoleUniqueException;
import io.github.egorshramko.booking.model.security.Role;
import io.github.egorshramko.booking.repository.security.RoleRepository;
import io.github.egorshramko.booking.service.security.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public Role addRole(Role role) throws EmptyRequiredFieldException {

        log.info("Start adding role");
        if (role.getName() == null) {
            log.warn("Role name is empty");
            throw new EmptyRequiredFieldException("Role name is empty");
        }

        log.info("Searching role with name {}", role.getName());
        //Поиск роли с аналогичным названием
        Optional<Role> roleOptional = roleRepository.findByName(role.getName());
        if (roleOptional.isEmpty()) {
            log.info("Role not found");
            role.setActual(true);
            return roleRepository.save(role);
        }
        else {
            Role roleEntity = roleOptional.get();
            if (roleEntity.isActual()) {
                log.warn("Role found");
                throw new RoleUniqueException("Role with name " + role.getName() + " is already exists");
            }
            else {
                log.info("Found not actual role");
                roleEntity.setActual(true);
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
        Role roleEntity = roleRepository.findByIdAndActualIsTrue(role.getId())
                .orElseThrow(() -> new EntityNotFoundException("Role with id " + role.getId() + " not found"));

        roleEntity.setName(role.getName());
        return roleRepository.save(roleEntity);

    }

    @Override
    @Transactional
    public void removeRole(Long roleId) {

        log.info("Start removing role with id {}", roleId);
        Optional<Role> roleOptional = roleRepository.findByIdAndActualIsTrue(roleId);
        if (roleOptional.isPresent()) {
            log.info("Role for removing found");

            Role removingRole = roleOptional.get();
            removingRole.setActual(false);
            roleRepository.save(removingRole);
        }
        else {
            log.info("Role for removing not found");
        }
    }

    @Override
    public Page<Role> getRolePage(Integer pageNumber) {
        return roleRepository.findAll(PageRequest.of(pageNumber, 20, Sort.by("name").ascending()));
    }
}
