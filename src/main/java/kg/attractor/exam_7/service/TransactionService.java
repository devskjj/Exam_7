package kg.attractor.exam_7.service;

import kg.attractor.exam_7.dto.transaction.TransactionRequestDto;
import kg.attractor.exam_7.dto.transaction.TransactionViewDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionService {
    @Transactional
    void makeTransaction(TransactionRequestDto request, String username);

    List<TransactionViewDto> getAccountHistory(Integer accountId);
}
