package kg.attractor.exam_7.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Integer id;

    @NotNull(message = "Пользователь обязателен")
    private UserDto user;

    @NotBlank(message = "Номер счета обязателен")
    @Size(max = 36, message = "Номер счета должен содержать от 10 до 20 символов")
    private String accountNumber;

    @NotNull(message = "Валюта обязательна")
    private CurrencyDto currency;

    @PositiveOrZero(message = "Баланс не может быть отрицательным")
    private BigDecimal balance;

    private LocalDateTime createdAt;
}
