package kg.attractor.exam_7.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class TransactionRollbackDto {
    private Integer id;

    @NotNull(message = "Транзакция обязательна")
    private TransactionDto transaction;

    @NotNull(message = "Пользователь, отменивший транзакцию, обязателен")
    private UserDto rolledBackBy;

    private LocalDateTime createdAt;
}