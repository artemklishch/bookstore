package org.example.intro.service;

import org.example.intro.dto.user.UserRegistrationRequestDto;
import org.example.intro.dto.user.UserResponseDto;
import org.example.intro.exceptions.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}
