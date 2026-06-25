package io.github.egorshramko.booking.facade;

import io.github.egorshramko.booking.dto.admin.AdminCreateCinemaRequest;
import io.github.egorshramko.booking.dto.admin.AdminCreateCinemaResponse;

public interface AdminCinemaFacade {
    AdminCreateCinemaResponse createCinema(AdminCreateCinemaRequest request);
}
