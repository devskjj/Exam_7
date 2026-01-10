package kg.attractor.exam_7.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    private Integer id;

    @NotNull(message = "Счет отправителя обязателен")
    private AccountDto fromAccount;

    @NotNull(message = "Счет получателя обязателен")
    private AccountDto toAccount;

    @Positive(message = "Сумма должна быть положительной")
    @NotNull(message = "Сумма обязательна")
    private BigDecimal amount;

    @NotNull(message = "Валюта обязательна")
    private CurrencyDto currency;

    @NotBlank(message = "Статус обязателен")
    private String status;

    private Boolean approved;

    private UserDto approvedBy;

    @NotBlank(message = "Тип транзакции обязателен")
    private String transactionType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
