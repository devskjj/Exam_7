package kg.attractor.exam_7.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NotBlank(message = "Имя пользователя обязательно")
    @Size(max = 55, message = "Имя пользователя должно содержать до 55 символов.")
    private String username;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 12, max = 24, message = "Длина пароля от 12 до 24 символов")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,24}$",
            message = "Пароль должен содержать от 12 до 24 символов, минимум одну заглавную букву, одну строчную, одну цифру и один спецсимвол"
    )
    private String password;

    @NotBlank(message = "Номер телефона обязателен")
    @Pattern(
            regexp = "^996 \\(\\d{3}\\) \\d{2}-\\d{2}-\\d{2}$",
            message = "Телефон должен быть в формате 996 (XXX) XX-XX-XX"
    )
    private String phoneNumber;

}
