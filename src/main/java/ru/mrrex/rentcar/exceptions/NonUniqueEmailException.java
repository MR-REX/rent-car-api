package ru.mrrex.rentcar.exceptions;

public class NonUniqueEmailException extends Exception {

    public NonUniqueEmailException() {
        super("Email isn't unique");
    }

    public NonUniqueEmailException(String email) {
        super("Email \"%s\" isn't unique".formatted(email));
    }
}
