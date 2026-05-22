package io.github.egorshramko.booking.exception;

public class RoleUniqueException extends RuntimeException {
    public RoleUniqueException(String message) {
        super(message);
    }
}
