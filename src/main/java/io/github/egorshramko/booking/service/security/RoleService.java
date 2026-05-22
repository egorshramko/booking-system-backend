package io.github.egorshramko.booking.service.security;

import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.model.security.Role;
import org.springframework.data.domain.Page;

public interface RoleService {

    /**
     * Метод добавления роли в систему
     * @param role - добавляемая роль
     * @return добавленная роль
     */
    Role addRole(Role role) throws EmptyRequiredFieldException;

    /**
     * Метод получения роли по ID
     * @param roleId - ID роли
     * @return найденная роль
     */
    Role getRoleById(Long roleId);

    /**
     * Метод редактирования роли
     * @param role - редактируемая роль
     * @return отредактированная роль
     */
    Role editRole(Role role) throws EmptyRequiredFieldException;

    /**
     * Метод удаления роли по ID
     * @param roleId - ID роли
     */
    void removeRole(Long roleId);

    /**
     * Метод получения страницы ролей
     * @param pageNumber - номер страницы
     * @return страница ролей
     */
    Page<Role> getRolePage(Integer pageNumber);


}
