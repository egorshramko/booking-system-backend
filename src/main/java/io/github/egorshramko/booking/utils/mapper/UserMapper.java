package io.github.egorshramko.booking.utils.mapper;

import io.github.egorshramko.booking.dto.RoleDto;
import io.github.egorshramko.booking.dto.admin.*;
import io.github.egorshramko.booking.model.security.User;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = RoleMapper.class)
public interface UserMapper {

    User toUserEntityFromAdminCreateUserDto(AdminCreateUserRequestUserDto userDto);
    User toUserEntityFromAdminUpdateUserDto(AdminUpdateUserRequestUserDto userDto);

    @Mapping(target = "username",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromAdminUpdateUserDto(AdminUpdateUserRequestUserDto userDto, @MappingTarget User user);

    AdminCreateUserResponse toAdminCreateUserResponse(User user);
    AdminUserDataResponse toAdminUserDataResponse(User user);

    AdminGetUsersResponseUserDto toAdminGetUsersResponseUserDto(User user);

    List<AdminGetUsersResponseUserDto> toAdminGetUsersResponseUserDtoList(List<User> users);

}
