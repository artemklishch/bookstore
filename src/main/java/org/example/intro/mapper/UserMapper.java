package org.example.intro.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.intro.config.MapperConfig;
import org.example.intro.dto.user.UserRegistrationRequestDto;
import org.example.intro.dto.user.UserResponseDto;
import org.example.intro.model.Role;
import org.example.intro.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toUserResponseDto(User user);

    @Mapping(target = "roles", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);

    @AfterMapping
    default void setRoles(@MappingTarget User user, UserRegistrationRequestDto responseDto) {
        Set<Long> roleIds = new HashSet<>(responseDto.getRoles());
        Set<Role> roles = roleIds.stream()
                .map(Role::new)
                .collect(Collectors.toSet());
        user.setRoles(roles);
    }
}
