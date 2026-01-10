package kg.attractor.exam_7.controller;

import kg.attractor.exam_7.dto.transaction.TransactionViewDto;
import kg.attractor.exam_7.exception.AccessDeniedException;
import kg.attractor.exam_7.exception.TransactionNotFoundException;
import kg.attractor.exam_7.service.AdminTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/transactions")
public class AdminController {
    private final AdminTransactionService adminTransactionService;

    @GetMapping
    public ResponseEntity<List<TransactionViewDto>> getAllTransactions() {
        return ResponseEntity.ok(adminTransactionService.getAllTransactions());
    }

    @GetMapping("/approval")
    public ResponseEntity<List<TransactionViewDto>> getTransactionsRequiringApproval() {
        return ResponseEntity.ok(adminTransactionService.getTransactionsRequiringApproval());
    }

    @PostMapping("/approval")
    public ResponseEntity<?> approveTransaction(@RequestParam Integer transactionId) {
        adminTransactionService.approveTransaction(transactionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rollback")
    public ResponseEntity<?> rollbackTransaction(@RequestParam Integer transactionId) {
        try {
            adminTransactionService.rollbackTransaction(transactionId);
            return ResponseEntity.ok().build();
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (TransactionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Integer id) {
        adminTransactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
