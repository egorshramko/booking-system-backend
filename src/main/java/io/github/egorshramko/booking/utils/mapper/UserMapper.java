package io.github.egorshramko.booking.utils.mapper;

import io.github.egorshramko.booking.dto.admin.AdminCreateUserRequestUserDto;
import io.github.egorshramko.booking.model.security.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toUserEntityFromAdminCreateUserDto(AdminCreateUserRequestUserDto userDto);

}
