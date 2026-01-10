package kg.attractor.exam_7.service;

import kg.attractor.exam_7.dto.transaction.TransactionViewDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminTransactionService {
    List<TransactionViewDto> getAllTransactions();

    List<TransactionViewDto> getTransactionsRequiringApproval();

    @Transactional
    void approveTransaction(Integer transactionId);

    @Transactional
    void rollbackTransaction(Integer transactionId);

    @Transactional
    void deleteTransaction(Integer rollbackId);
}
