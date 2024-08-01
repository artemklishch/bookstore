package org.example.intro.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.intro.dto.user.UserRegistrationRequestDto;
import org.example.intro.dto.user.UserResponseDto;
import org.example.intro.exceptions.RegistrationException;
import org.example.intro.mapper.UserMapper;
import org.example.intro.model.User;
import org.example.intro.repository.role.RoleRepository;
import org.example.intro.repository.user.UserRepository;
import org.example.intro.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException(
                    "Can't register user with email "
                            + requestDto.getEmail()
                            + ". Email is already in use."
            );
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }
}
