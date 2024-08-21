package org.example.intro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.user.UserLoginRequestDto;
import org.example.intro.dto.user.UserLoginResponseDto;
import org.example.intro.dto.user.UserRegistrationRequestDto;
import org.example.intro.dto.user.UserResponseDto;
import org.example.intro.exceptions.RegistrationException;
import org.example.intro.sequrity.AuthenticationService;
import org.example.intro.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Authentication management",
        description = "Endpoints for user authentication management"
)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @Operation(summary = "Register user", description = "Registers new user")
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto requestDto
    ) throws RegistrationException {
        return userService.register(requestDto);
    }

    @Operation(summary = "Login user", description = "Login user")
    @PostMapping("/login")
    public UserLoginResponseDto login(
            @RequestBody @Valid UserLoginRequestDto requestDto
    ) {
        return authenticationService.authenticate(requestDto);
    }
}
