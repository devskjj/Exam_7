package kg.attractor.exam_7.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private Integer id;
    private Integer fromAccountId;
    private Integer toAccountId;
    private BigDecimal amount;
    private Integer currencyId;
    private String status;
    private Boolean approved;
    private Integer approvedByUserId;
    private String transactionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
