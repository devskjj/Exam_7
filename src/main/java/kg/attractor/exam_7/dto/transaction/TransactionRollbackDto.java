package kg.attractor.exam_7.dto.transaction;

import jakarta.validation.constraints.NotNull;
import kg.attractor.exam_7.dto.UserDto;
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
    private TransactionDetailDto transaction;

    @NotNull(message = "Пользователь, отменивший транзакцию, обязателен")
    private UserDto rolledBackBy;

    private LocalDateTime createdAt;
}