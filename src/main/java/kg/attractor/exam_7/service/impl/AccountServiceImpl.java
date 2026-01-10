package kg.attractor.exam_7.service.impl;

import kg.attractor.exam_7.dao.AccountDao;
import kg.attractor.exam_7.dao.CurrencyDao;
import kg.attractor.exam_7.dto.AccountDto;
import kg.attractor.exam_7.dto.CreateAccountDto;
import kg.attractor.exam_7.dto.CurrencyDto;
import kg.attractor.exam_7.dto.UserDto;
import kg.attractor.exam_7.service.AccountService;
import kg.attractor.exam_7.util.AuthAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
