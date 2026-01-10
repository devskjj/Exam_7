package kg.attractor.exam_7.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor()
@NoArgsConstructor
@Builder
public class TransactionDisplayDto {
    private Integer id;
    private Integer fromAccount;
    private Integer toAccount;
    private BigDecimal amount;
    private String currency;
    private String status;
    private Boolean approved;
    private Integer approvedBy;
    private String transactionType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
