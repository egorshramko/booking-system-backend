package io.github.egorshramko.booking.facade.impl;

import io.github.egorshramko.booking.dto.admin.AdminCreateUserRequest;
import io.github.egorshramko.booking.dto.admin.AdminCreateUserResponse;
import io.github.egorshramko.booking.dto.admin.AdminUpdateUserRequest;
import io.github.egorshramko.booking.dto.admin.AdminUserDataResponse;
import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.facade.AdminUserFacade;
import io.github.egorshramko.booking.model.security.Role;
import io.github.egorshramko.booking.model.security.User;
import io.github.egorshramko.booking.service.security.UserEntityService;
import io.github.egorshramko.booking.utils.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

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
}
