package org.example.intro.service.impl;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.user.UserRegistrationRequestDto;
import org.example.intro.dto.user.UserResponseDto;
import org.example.intro.exceptions.RegistrationException;
import org.example.intro.mapper.UserMapper;
import org.example.intro.model.Role;
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
        passwordEncoder.encode(requestDto.getPassword());
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Set<String> rolesNames = new HashSet<>(requestDto.getRoles());
        Set<Role> roles = new HashSet<>();
        for (String roleName : rolesNames) {
            Role.RoleName roleEnum = Role.RoleName.get(roleName);
            Role role = roleRepository.findByName(roleEnum)
                    .orElseThrow(
                            () -> new RegistrationException("Role not found: " + roleName)
                    );
            roles.add(role);
        }
        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }
}
