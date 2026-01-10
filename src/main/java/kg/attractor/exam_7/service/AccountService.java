package kg.attractor.exam_7.service;

import kg.attractor.exam_7.dto.CreateAccountDto;
import kg.attractor.exam_7.dto.DepositRequestDto;
import kg.attractor.exam_7.dto.AccountResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountService {
    Integer createAccount(CreateAccountDto accountDto);

    AccountResponse getBalance(String accountNumber);

    List<AccountResponse> getUserAccounts();

    @Transactional
    AccountResponse depositToAccount(DepositRequestDto request);
}
