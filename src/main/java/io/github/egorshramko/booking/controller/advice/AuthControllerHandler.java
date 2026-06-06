package io.github.egorshramko.booking.controller.advice;

import io.github.egorshramko.booking.dto.error.ErrorResponse;
import jakarta.security.auth.message.AuthException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.http.HttpStatusCode;

@RestControllerAdvice
public class AuthControllerHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException exception) {
        return ResponseEntity.status(HttpStatusCode.UNAUTHORIZED)
                .body(new ErrorResponse(exception.getMessage()));
    }

}
