package kg.attractor.exam_7.service;

import jakarta.validation.Valid;
import kg.attractor.exam_7.dto.CreateAccountDto;

public interface AccountService {
    void createAccount(CreateAccountDto accountDto);
}
