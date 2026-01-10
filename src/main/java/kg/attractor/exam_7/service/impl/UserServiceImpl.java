package kg.attractor.exam_7.service.impl;

import kg.attractor.exam_7.dao.UserDao;
import kg.attractor.exam_7.dto.UserDto;
import kg.attractor.exam_7.exception.InvalidUserDataException;
import kg.attractor.exam_7.exception.UserNotFoundException;
import kg.attractor.exam_7.mapper.interfaces.UserMapper;
import kg.attractor.exam_7.model.User;
import kg.attractor.exam_7.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public Integer register(UserDto userDto) {
        log.info("Валидация пользователя по номеру телефона: {}", userDto.getPhoneNumber());
        validateUser(userDto);

        User user = userMapper.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("Пользователь {} зарегистрирован", userDto.getUsername());
        return userDao.register(userDto);
    }

    @Override
    public void validateUser(UserDto userDto) {
        if (userDao.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new InvalidUserDataException("Номер телефона уже занят");
        }
    }

    @Override
    public UserDto getUserByPhoneNumber(String phoneNumber) {
        return userMapper.toDto(userDao.getUserByPhoneNumber(phoneNumber).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public UserDto getUserById(Integer userId) {
        return userMapper.toDto(userDao.getUserById(userId).orElseThrow(UserNotFoundException::new));
    }
}
