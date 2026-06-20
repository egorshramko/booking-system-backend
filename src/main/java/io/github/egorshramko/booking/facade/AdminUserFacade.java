package io.github.egorshramko.booking.facade;

import io.github.egorshramko.booking.dto.admin.AdminCreateUserRequest;
import io.github.egorshramko.booking.dto.admin.AdminCreateUserResponse;
import io.github.egorshramko.booking.dto.admin.AdminGetUserResponse;
import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;

public interface AdminUserFacade {

    AdminCreateUserResponse createUser(AdminCreateUserRequest request) throws EmptyRequiredFieldException;

    AdminGetUserResponse getUserById(Long id);

}
