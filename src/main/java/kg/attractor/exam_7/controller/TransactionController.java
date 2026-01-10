package kg.attractor.exam_7.controller;

import jakarta.validation.Valid;
import kg.attractor.exam_7.dto.TransactionRequest;
import kg.attractor.exam_7.dto.TransactionDisplayDto;
import kg.attractor.exam_7.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/{accountId}/history")
    public ResponseEntity<List<TransactionDisplayDto>> getTransactionHistory(@PathVariable Integer accountId) {
        return ResponseEntity.ok(transactionService.getAccountHistory(accountId));
    }

    @PostMapping
    public ResponseEntity<String> makeTransaction(@RequestBody @Valid TransactionRequest request, Authentication authentication) {
        transactionService.makeTransaction(request, authentication.getName());
        return ResponseEntity.ok("Transaction was successful");
    }

}
