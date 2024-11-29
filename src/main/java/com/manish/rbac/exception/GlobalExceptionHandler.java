package com.manish.rbac.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling various exceptions across the application.
 * This class is annotated with @RestControllerAdvice to handle exceptions and return appropriate responses
 * to the client.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the exception thrown when a user already exists.
     * It returns an HTTP response with status 417 (Expectation Failed) and the exception message.
     *
     * @param exception the exception thrown when a user already exists.
     * @return a ProblemDetail containing the status and the exception message.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        // Return a ProblemDetail with a specific status and the exception message
        return ProblemDetail.forStatusAndDetail(HttpStatus.EXPECTATION_FAILED, exception.getMessage());
    }

    /**
     * Handles the exception thrown when a user is not found.
     * It returns an HTTP response with status 400 (Bad Request) and the exception message.
     *
     * @param exception the exception thrown when a user is not found.
     * @return a ProblemDetail containing the status and the exception message.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(UserNotFoundException exception){
        // Return a ProblemDetail with a specific status and the exception message
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
