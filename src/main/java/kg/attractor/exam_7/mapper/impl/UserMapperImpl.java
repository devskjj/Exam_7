package kg.attractor.exam_7.mapper.impl;

import kg.attractor.exam_7.dto.UserDto;
import kg.attractor.exam_7.mapper.UserMapper;
import kg.attractor.exam_7.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if (user == null) return null;

        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setPassword(user.getPassword());
        return dto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        if (userDto == null) return null;

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());
        return user;
    }
}