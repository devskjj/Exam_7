package kg.attractor.exam_7.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    private Integer id;
    private Integer userId;
    private String accountNumber;
    private Integer currencyId;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
