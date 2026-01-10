package kg.attractor.exam_7.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositRequestDto {
    @NotBlank(message = "Номер счета обязателен")
    @Size(max = 36, message = "Номер счета должен содержать до 36 символов")
    private String accountNumber;

    @Positive(message = "Сумма пополнения должна быть положительной")
    @NotNull(message = "Сумма пополнения обязательна")
    private BigDecimal amount;

}
