package io.github.egorshramko.booking.facade;

import io.github.egorshramko.booking.dto.PermissionDto;
import io.github.egorshramko.booking.dto.RoleDto;
import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

/**
 * Фасад для обращения к сервису ролей.
 * Используется для преобразования данных из DTO в полноценный объект сущности
 * и вызова методов сервиса
 */
public interface RoleServiceFacade {

    /**
     * Метод добавления новой роли
     * @param roleDto - запрос с данными роли
     * @return данные роли в формате DTO
     */
    RoleDto addRole(RoleDto roleDto) throws EmptyRequiredFieldException;

    /**
     * Метод получения роли по ID
     * @param roleId - ID роли
     * @return данные роли в формате DTO
     */
    RoleDto getRoleById(Long roleId);

    /**
     * Метод редактирования роли
     * @param roleId - ID роли
     * @param roleDto - данные роли для редактирования в формате DTO
     * @return отредактированные данные роли в формате DTO
     */
    RoleDto editRole(Long roleId, RoleDto roleDto) throws EmptyRequiredFieldException;

    /**
     * Метод удаления роли по ID
     * @param roleId - ID роли
     */
    void removeRole(Long roleId);

    /**
     * Метод получения страницы роли по номеру страницы
     * @param pageNumber - номер страницы
     * @return страница с ролями в формате DTO
     */
    Set<RoleDto> getRolePage(Integer pageNumber);


}
