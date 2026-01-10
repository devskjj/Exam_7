package kg.attractor.exam_7.dto.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestDto {
    @NotBlank(message = "Номер счета отправителя обязателен")
    private String fromAccountNumber;

    @NotBlank(message = "Номер счета получателя обязателен")
    private String toAccountNumber;

    @Positive(message = "Сумма должна быть положительной")
    private BigDecimal amount;
}
