package io.github.egorshramko.booking.exception;

public class EmptyRequiredFieldException extends Exception {
    public EmptyRequiredFieldException(String message) {
        super(message);
    }
}
