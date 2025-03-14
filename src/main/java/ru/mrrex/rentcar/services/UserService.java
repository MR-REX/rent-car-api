package ru.mrrex.rentcar.services;

import java.util.Optional;
import ru.mrrex.rentcar.dto.requests.RegisterRequest;
import ru.mrrex.rentcar.exceptions.NonUniqueEmailException;
import ru.mrrex.rentcar.exceptions.UsernameAlreadyTakenException;
import ru.mrrex.rentcar.models.User;

public interface UserService {

    User createUser(RegisterRequest registerRequest) throws UsernameAlreadyTakenException, NonUniqueEmailException;

    Optional<User> getUserByUsername(String username);
    Optional<User> getCurrentUser();
}
