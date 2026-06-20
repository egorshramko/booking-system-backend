package io.github.egorshramko.booking.dto.admin;

//DTO пользователя в запросе на создание пользователя от администратора
public record AdminCreateUserRequestUserDto(
        String username,
        String password
) {
}
