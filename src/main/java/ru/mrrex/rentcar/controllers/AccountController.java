package ru.mrrex.rentcar.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import ru.mrrex.rentcar.dto.responses.AccountResponse;
import ru.mrrex.rentcar.exceptions.ApplicationError;
import ru.mrrex.rentcar.services.UserService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @GetMapping
    public AccountResponse getAccount() {
        return userService.getCurrentUser().map(user -> {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setUsername(user.getUsername());
            accountResponse.setEmail(user.getEmail());

            return accountResponse;
        }).orElseThrow(() -> new ApplicationError(HttpStatus.UNAUTHORIZED,
                "Failed to get access for current user"));
    }
}
