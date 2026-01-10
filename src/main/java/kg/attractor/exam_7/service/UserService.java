package kg.attractor.exam_7.service;

import kg.attractor.exam_7.dto.UserDto;

public interface UserService {

    Integer register(UserDto userDto);

    void validateUser(UserDto userDto);
}
