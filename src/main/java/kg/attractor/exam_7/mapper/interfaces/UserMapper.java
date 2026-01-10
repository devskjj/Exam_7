package kg.attractor.exam_7.mapper.interfaces;

import kg.attractor.exam_7.dto.UserDto;
import kg.attractor.exam_7.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
