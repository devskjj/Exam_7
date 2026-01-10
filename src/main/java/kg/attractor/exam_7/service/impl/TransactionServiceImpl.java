package kg.attractor.exam_7.service.impl;

import kg.attractor.exam_7.dao.AccountDao;
import kg.attractor.exam_7.dao.TransactionDao;
import kg.attractor.exam_7.dto.AccountDto;
import kg.attractor.exam_7.dto.TransactionRequest;
import kg.attractor.exam_7.dto.TransactionDisplayDto;
import kg.attractor.exam_7.dto.TransactionDto;
import kg.attractor.exam_7.exception.AccessDeniedException;
import kg.attractor.exam_7.exception.AccountNotFoundException;
import kg.attractor.exam_7.service.ExchangeRateService;
import kg.attractor.exam_7.service.TransactionService;
import kg.attractor.exam_7.util.AuthAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountDao accountDao;
    private final TransactionDao transactionDao;
    private final AuthAdapter authAdapter;
    private final ExchangeRateService exchangeRateService;

    @Transactional
    @Override
    public void makeTransaction(TransactionRequest request, String username) {
        String fromAccNum = request.getFromAccountNumber();
        String toAccNum = request.getToAccountNumber();
        BigDecimal amount = request.getAmount();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }


        AccountDto from = accountDao.findByAccountNumber(fromAccNum)
                .orElseThrow(() -> new AccountNotFoundException("Счет отправителя не найден"));

        AccountDto to = accountDao.findByAccountNumber(toAccNum)
                .orElseThrow(() -> new AccountNotFoundException("Счет получателя не найден"));

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Недостаточно средств для выполнения транзакции");
        }


        BigDecimal convertedAmount = amount;

        if (!from.getCurrency().getCode().equals(to.getCurrency().getCode())) {
            BigDecimal exchangeRate = exchangeRateService.getExchangeRate(
                    from.getCurrency().getCode(),
                    to.getCurrency().getCode()
            );

            convertedAmount = amount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
            ;
        }


        boolean approved = amount.compareTo(BigDecimal.valueOf(10000.0)) > 0;

        LocalDateTime now = LocalDateTime.now();

        TransactionDto transaction = TransactionDto.builder()
                .fromAccount(from)
                .toAccount(to)
                .currency(to.getCurrency())
                .amount(convertedAmount)
                .status(approved ? "PENDING" : "COMPLETED")
                .approved(approved)
                .transactionType("TRANSFER")
                .createdAt(now)
                .updatedAt(now)
                .build();

        transactionDao.create(transaction);

        if (!approved) {
            BigDecimal newFromBalance = from.getBalance().subtract(amount);
            BigDecimal newToBalance = to.getBalance().add(convertedAmount);

            accountDao.updateBalance(from.getId(), newFromBalance);
            accountDao.updateBalance(to.getId(), newToBalance);
        }

    }

    public List<TransactionDisplayDto> getAccountHistory(Integer accountId) {
        AccountDto account = accountDao.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.valueOf(accountId)));

        if (!account.getUser().getId().equals(authAdapter.getAuthId())) {
            throw new AccessDeniedException("Нет доступа к истории операций");
        }

        return transactionDao.findHistoryByAccountId(accountId);
    }
}
