package io.github.egorshramko.booking.dto.admin;

public record AdminCreateCinemaRequest(
        String name,
        String address,
        String city,
        String photoFilename,
        String managerUsername
) {}
