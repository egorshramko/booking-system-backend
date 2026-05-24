package io.github.egorshramko.booking.facade;

import io.github.egorshramko.booking.dto.PermissionDto;

import java.util.Set;

public interface PermissionServiceFacade {

    /**
     * Метод получения списка разрешений, существующих в системе
     * @return полный список разрешений
     */
    Set<PermissionDto> getAllSystemPermissions();

}
