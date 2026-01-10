package kg.attractor.exam_7.service.impl;

import kg.attractor.exam_7.dao.AccountDao;
import kg.attractor.exam_7.dao.CurrencyDao;
import kg.attractor.exam_7.dto.CreateAccountDto;
import kg.attractor.exam_7.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final CurrencyDao currencyDao;

    @Override
    public void createAccount(CreateAccountDto accountDto) {

    }
}
