package ru.mrrex.rentcar.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.mrrex.rentcar.dto.responses.AccountResponse;
import ru.mrrex.rentcar.exceptions.ApplicationError;
import ru.mrrex.rentcar.services.UserService;

@RestController
@RequestMapping("/api/v1/account")
@Tag(name = "Account Controller",
        description = "Operations related to current account of an authorized user")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get account details", description = "Returns account details of current user")
    @ApiResponse(responseCode = "200", description = "User is logged in")
    @ApiResponse(responseCode = "401", description = "User isn't authorized")
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
