package io.github.egorshramko.booking.controller;

import io.github.egorshramko.booking.dto.RoleDto;
import io.github.egorshramko.booking.dto.admin.AdminCreateRoleRequest;
import io.github.egorshramko.booking.dto.admin.AdminCreateRoleResponse;
import io.github.egorshramko.booking.dto.admin.AdminPermissionsResponse;
import io.github.egorshramko.booking.dto.PermissionDto;
import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.facade.PermissionServiceFacade;
import io.github.egorshramko.booking.facade.RoleServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public final class AdminController {

    @Autowired
    private PermissionServiceFacade permissionService;

    @Autowired
    private RoleServiceFacade roleService;

    @GetMapping("permissions")
    public ResponseEntity<AdminPermissionsResponse> fetchAllSystemPermissions() {
        final Set<PermissionDto> permissions = permissionService.getAllSystemPermissions();
        final AdminPermissionsResponse responseBody = new AdminPermissionsResponse(permissions);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("role")
    public ResponseEntity<AdminCreateRoleResponse> createRole(@RequestBody AdminCreateRoleRequest request)
            throws EmptyRequiredFieldException {

        final RoleDto responseData = roleService.addRole(
                new RoleDto(null, request.name(), new HashSet<>(request.permissions())));
        final AdminCreateRoleResponse responseBody = new AdminCreateRoleResponse(
                true,
                "Role created successfully",
                responseData);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("role/{id}")
    public ResponseEntity<RoleDto> fetchRole(@PathVariable Long id) {
        final RoleDto responseBody = roleService.getRoleById(id);
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("role/{id}")
    public ResponseEntity<RoleDto> editRole(@PathVariable Long id,
                                            @RequestBody RoleDto request) throws EmptyRequiredFieldException {

        final RoleDto responseBody = roleService.editRole(id, request);
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("role/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.removeRole(id);
        return ResponseEntity.noContent()
                .build();
    }



}
