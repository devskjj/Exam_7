package kg.attractor.exam_7.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rollback {
    private Integer id;
    private Integer transactionId;
    private Integer byUserId;
    private LocalDateTime createdAt;
}
