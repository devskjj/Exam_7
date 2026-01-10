package kg.attractor.exam_7.service.impl;

import kg.attractor.exam_7.dao.AccountDao;
import kg.attractor.exam_7.dao.CurrencyDao;
import kg.attractor.exam_7.dao.TransactionDao;
import kg.attractor.exam_7.dto.*;
import kg.attractor.exam_7.service.AccountService;
import kg.attractor.exam_7.util.AuthAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AuthAdapter authAdapter;
    private final AccountDao accountDao;
    private final CurrencyDao currencyDao;
    private final TransactionDao transactionDao;

    @Override
    public Integer createAccount(CreateAccountDto accountDto) {
        UserDto user = authAdapter.getAuthUser();
        String currencyCode = accountDto.getCurrencyCode();

        CurrencyDto currency = currencyDao.findByCode(currencyCode)
                .orElseThrow(() -> new RuntimeException("Валюта не найдена"));

        List<AccountDto> userAccounts = accountDao.findAllByUserId(user.getId());

        if (userAccounts.size() >= 3) {
            throw new RuntimeException("Нельзя создать больше 3 счетов");
        }

        boolean exists = userAccounts.stream()
                .anyMatch(acc -> acc.getCurrency().getCode().equals(currencyCode));

        if (exists) {
            throw new RuntimeException("Счет в этой валюте уже существует");
        }

        String accountNumber = UUID.randomUUID().toString();

        AccountDto newAccount = AccountDto.builder()
                .user(user)
                .currency(currency)
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();

        return accountDao.create(newAccount);
    }

    @Override
    public AccountResponse getBalance(String accountNumber) {
        AccountDto account = accountDao.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Счет не найден"));

        if (!account.getUser().getId().equals(authAdapter.getAuthId())) {
            throw new RuntimeException("Нет доступа к этому счету");
        }

        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .currency(account.getCurrency().getCode())
                .balance(account.getBalance())
                .build();
    }

    @Override
    public List<AccountResponse> getUserAccounts() {
        Integer userId = authAdapter.getAuthId();
        return accountDao.findAllByUserId(userId).stream()
                .map(account -> AccountResponse.builder()
                        .id(account.getId())
                        .accountNumber(account.getAccountNumber())
                        .currency(account.getCurrency().getCode())
                        .balance(account.getBalance())
                        .build())
                .toList();
    }

    @Transactional
    @Override
    public AccountResponse depositToAccount(DepositRequestDto request) {
        UserDto currentUser = authAdapter.getAuthUser();

        AccountDto account = accountDao.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Счет не найден"));

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Нельзя пополнять чужие счета");
        }

        BigDecimal newBalance = account.getBalance().add(request.getAmount());
        accountDao.updateBalance(account.getId(), newBalance);


        TransactionDto transaction = TransactionDto.builder()
                .fromAccount(null)
                .toAccount(AccountDto.builder()
                        .id(account.getId())
                        .accountNumber(account.getAccountNumber())
                        .build())
                .amount(request.getAmount())
                .currency(account.getCurrency())
                .status("COMPLETED")
                .approved(true)
                .approvedBy(currentUser)
                .transactionType("DEPOSIT")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        transactionDao.create(transaction);

        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .currency(account.getCurrency().getCode())
                .balance(newBalance)
                .build();
    }
}
