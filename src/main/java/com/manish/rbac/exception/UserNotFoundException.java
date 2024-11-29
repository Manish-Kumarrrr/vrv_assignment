package com.manish.rbac.exception;

/**
 * Custom exception class for handling scenarios where a user is not found in the system.
 * This exception extends RuntimeException, making it an unchecked exception.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructor to create an exception with a custom message.
     *
     * @param message the message describing the exception.
     */
    public UserNotFoundException(String message) {
        // Pass the message to the superclass (RuntimeException)
        super(message);
    }
}
