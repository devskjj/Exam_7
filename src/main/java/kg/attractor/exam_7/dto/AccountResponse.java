package kg.attractor.exam_7.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private Integer id;
    private String accountNumber;
    private String currency;
    private BigDecimal balance;
}