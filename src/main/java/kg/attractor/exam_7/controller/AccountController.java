package kg.attractor.exam_7.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import kg.attractor.exam_7.dto.CreateAccountDto;
import kg.attractor.exam_7.dto.DepositRequestDto;
import kg.attractor.exam_7.dto.AccountResponse;
import kg.attractor.exam_7.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/balance")
    public ResponseEntity<AccountResponse> getBalance(@RequestParam @NotBlank String accountNumber) {
        return ResponseEntity.ok(accountService.getBalance(accountNumber));
    }

    @PostMapping("/balance")
    public ResponseEntity<AccountResponse> depositToAccount(@Valid @RequestBody DepositRequestDto request) {
        return ResponseEntity.ok(accountService.depositToAccount(request));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getUserAccounts() {
        return ResponseEntity.ok(accountService.getUserAccounts());
    }

}
