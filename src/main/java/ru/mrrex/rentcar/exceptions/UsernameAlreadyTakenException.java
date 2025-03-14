package ru.mrrex.rentcar.exceptions;

public class UsernameAlreadyTakenException extends Exception {

    public UsernameAlreadyTakenException() {
        super("Username already taken");
    }

    public UsernameAlreadyTakenException(String username) {
        super("Username \"%s\" already taken".formatted(username));
    }
}
