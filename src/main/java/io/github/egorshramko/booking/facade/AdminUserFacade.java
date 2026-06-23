package io.github.egorshramko.booking.facade;

import io.github.egorshramko.booking.dto.admin.*;
import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;

import javax.management.relation.RoleNotFoundException;

public interface AdminUserFacade {

    AdminCreateUserResponse createUser(AdminCreateUserRequest request) throws EmptyRequiredFieldException;

    AdminUserDataResponse getUserById(Long id);

    AdminUserDataResponse updateUserById(Long id, AdminUpdateUserRequest request);

    void removeUser(Long id);

    AdminGetUsersResponse getUsersPage(Integer pageNumber);

    AdminGetUsersPagesCountResponse getUsersPagesCount();

}
