package io.github.egorshramko.booking.controller;

import io.github.egorshramko.booking.dto.PermissionDto;
import io.github.egorshramko.booking.facade.PermissionServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionServiceFacade permissionService;

    @PreAuthorize("hasAuthority('READ_PERMISSION')")
    @GetMapping
    public Set<PermissionDto> getAllPermissions() {
        return permissionService.getAllSystemPermissions();
    }

}
