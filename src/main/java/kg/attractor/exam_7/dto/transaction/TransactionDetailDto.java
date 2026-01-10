    package kg.attractor.exam_7.dto.transaction;

    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Positive;
    import kg.attractor.exam_7.dto.CurrencyDto;
    import kg.attractor.exam_7.dto.UserDto;
    import kg.attractor.exam_7.dto.account.AccountDto;
    import lombok.*;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class TransactionDetailDto {
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
