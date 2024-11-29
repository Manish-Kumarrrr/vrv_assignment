package com.manish.rbac.exception;

/**
 * Custom exception class for handling scenarios where a user already exists in the system.
 * This exception extends RuntimeException, making it an unchecked exception.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructor to create an exception with a custom message.
     *
     * @param message the message describing the exception.
     */
    public UserAlreadyExistsException(String message) {
        // Pass the message to the superclass (RuntimeException)
        super(message);
    }
}
