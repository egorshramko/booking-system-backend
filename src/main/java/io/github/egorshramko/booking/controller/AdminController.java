package io.github.egorshramko.booking.controller;

import io.github.egorshramko.booking.dto.RoleDto;
import io.github.egorshramko.booking.dto.admin.*;
import io.github.egorshramko.booking.dto.PermissionDto;
import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.facade.AdminCinemaFacade;
import io.github.egorshramko.booking.facade.AdminUserFacade;
import io.github.egorshramko.booking.facade.PermissionServiceFacade;
import io.github.egorshramko.booking.facade.RoleServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
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

    @Autowired
    private AdminUserFacade adminUserFacade;

    @Autowired
    private AdminCinemaFacade adminCinemaFacade;

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

    @GetMapping("roles")
    public ResponseEntity<Set<RoleDto>> fetchRolePage(@RequestParam("page") Integer pageNumber) {
        final Set<RoleDto> rolesPage = roleService.getRolePage(pageNumber);
        return ResponseEntity.ok(rolesPage);
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

    @PostMapping("user")
    public ResponseEntity<AdminCreateUserResponse> createUser(@RequestBody AdminCreateUserRequest request)
            throws EmptyRequiredFieldException {
        final AdminCreateUserResponse responseBody = adminUserFacade.createUser(request);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<AdminUserDataResponse> getUser(@PathVariable Long id) {
        final AdminUserDataResponse responseBody = adminUserFacade.getUserById(id);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("users")
    public ResponseEntity<AdminGetUsersResponse> getUsersPage(@Param("page") Integer page) {
        AdminGetUsersResponse responseBody = adminUserFacade.getUsersPage(page - 1);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("users/pages")
    public ResponseEntity<AdminGetUsersPagesCountResponse> getUsersPagesCount() {
        final AdminGetUsersPagesCountResponse responseBody = adminUserFacade.getUsersPagesCount();

        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("user/{id}")
    public ResponseEntity<AdminUserDataResponse> updateUser(@PathVariable Long id,
                                                            @RequestBody AdminUpdateUserRequest requestBody) {

        final AdminUserDataResponse responseBody = adminUserFacade.updateUserById(id, requestBody);
        return ResponseEntity.ok(responseBody);

    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {

        adminUserFacade.removeUser(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("cinema")
    public ResponseEntity<AdminCreateCinemaResponse> createCinema(@RequestBody AdminCreateCinemaRequest requestBody) {

        AdminCreateCinemaResponse responseBody = adminCinemaFacade.createCinema(requestBody);
        return ResponseEntity.ok(responseBody);

    }

}
