package kg.attractor.exam_7.service;

import kg.attractor.exam_7.dto.TransactionRequest;
import kg.attractor.exam_7.dto.TransactionDisplayDto;
import org.jspecify.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionService {
    @Transactional
    void makeTransaction(TransactionRequest request, String username);

    @Nullable List<TransactionDisplayDto> getAccountHistory(Integer accountId);
}
