package io.github.egorshramko.booking.facade.impl;

import io.github.egorshramko.booking.dto.admin.*;
import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.facade.AdminUserFacade;
import io.github.egorshramko.booking.model.security.User;
import io.github.egorshramko.booking.service.security.UserEntityService;
import io.github.egorshramko.booking.utils.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AdminUserFacadeImpl implements AdminUserFacade {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserEntityService userEntityService;

    @Override
    public AdminCreateUserResponse createUser(AdminCreateUserRequest request) throws EmptyRequiredFieldException {

        final User user = userMapper.toUserEntityFromAdminCreateUserDto(request.user());
        final User savedUser = userEntityService.addUser(user);

        //TODO: организовать сохранение профиля

        return userMapper.toAdminCreateUserResponse(user);
    }

    @Override
    public AdminUserDataResponse getUserById(Long id) {
        final User user = userEntityService.getUserById(id);

        //TODO: организовать выгрузку данных профиля

        return userMapper.toAdminUserDataResponse(user);
    }

    @Override
    public AdminUserDataResponse updateUserById(Long id, AdminUpdateUserRequest request) {

        final User updatingUser = userEntityService.getUserById(id);
        userMapper.updateUserFromAdminUpdateUserDto(request.user(), updatingUser);
        final User updatedUser = userEntityService.editUser(updatingUser);

        //TODO: организовать редактирование профиля

        return userMapper.toAdminUserDataResponse(updatedUser);
    }

    @Override
    public void removeUser(Long id) {
        userEntityService.removeUser(id);
    }

    @Override
    public AdminGetUsersResponse getUsersPage(Integer pageNumber) {
        final Page<User> usersPage = userEntityService.getUsersPage(pageNumber);

        //преобразование страницы пользователей в список
        final List<User> usersList = usersPage.stream()
                .toList();

        final List<AdminGetUsersResponseUserDto> usersDtoList =
                userMapper.toAdminGetUsersResponseUserDtoList(usersList);

        return new AdminGetUsersResponse(usersDtoList);
    }

    @Override
    public AdminGetUsersPagesCountResponse getUsersPagesCount() {
        final Integer pagesCount = userEntityService.getUsersPagesCount();
        return new AdminGetUsersPagesCountResponse(pagesCount);
    }
}
