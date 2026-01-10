package kg.attractor.exam_7.service.impl;

import kg.attractor.exam_7.dao.UserDao;
import kg.attractor.exam_7.dto.UserDto;
import kg.attractor.exam_7.exception.InvalidUserDataException;
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

    @Override
    public Integer register(UserDto userDto) {
        log.info("Валидация пользователя по номеру телефона: {}", userDto.getPhoneNumber());
        validateUser(userDto);

        log.info("Пользователь {} зарегистрирован", userDto.getUsername());
        return userDao.register(userDto);
    }

    @Override
    public void validateUser(UserDto userDto) {
        if (userDao.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new InvalidUserDataException("Номер телефона уже занят");
        }
    }

}
