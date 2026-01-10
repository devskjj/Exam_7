package kg.attractor.exam_7.service;

import kg.attractor.exam_7.dto.account.CreateAccountDto;
import kg.attractor.exam_7.dto.DepositRequestDto;
import kg.attractor.exam_7.dto.account.AccountResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountService {
    Integer createAccount(CreateAccountDto accountDto);

    AccountResponseDto getBalance(String accountNumber);

    List<AccountResponseDto> getUserAccounts();

    @Transactional
    AccountResponseDto depositToAccount(DepositRequestDto request);
}
