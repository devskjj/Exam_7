package kg.attractor.exam_7.controller;

import jakarta.validation.Valid;
import kg.attractor.exam_7.dto.CreateAccountDto;
import kg.attractor.exam_7.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountDto accountDto) {
        accountService.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
    }

}
