package io.github.egorshramko.booking.exception;

public class UserUniqueException extends RuntimeException {
    public UserUniqueException(String message) {
        super(message);
    }
}
