package org.example.intro.mapper;

import org.example.intro.config.MapperConfig;
import org.example.intro.dto.user.UserResponseDto;
import org.example.intro.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toUserResponseDto(User user);
}
