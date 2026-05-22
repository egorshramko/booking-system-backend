package io.github.egorshramko.booking.service.security;

import io.github.egorshramko.booking.model.security.User;
import org.springframework.data.domain.Page;

public interface UserEntityService {

    /**
     * Метод добавления пользователя в систему
     * @param user - добавляемый пользователь
     * @return созданная сущность пользователя
     */
    User addUser(User user);

    /**
     * Метод получения пользователя по ID
     * @param userId - ID пользователя
     * @return найденный пользователь
     */
    User getUserById(Long userId);

    /**
     * Метод редактирования пользователя
     * @param user - редактируемый пользователь
     * @return отредактированный пользователь
     */
    User editUser(User user);

    /**
     * Метод удаления пользователя из системы
     * @param userId - ID пользователя
     */
    void removeUser(Long userId);

    /**
     * Метод получения страницы пользователей по номеру
     * @param pageNumber - номер страницы
     * @return Страница пользователей
     */
    Page<User> getUsersPage(Integer pageNumber);
}
