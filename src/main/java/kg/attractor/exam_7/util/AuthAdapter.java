package kg.attractor.exam_7.util;

import kg.attractor.exam_7.dto.UserDto;
import kg.attractor.exam_7.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthAdapter {

    private final UserService userService;

    public UserDto getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Пользователь не авторизирован");
        }

        return userService.getUserByPhoneNumber(authentication.getName());
    }

    public Integer getAuthId() {
        return getAuthUser().getId();
    }
}