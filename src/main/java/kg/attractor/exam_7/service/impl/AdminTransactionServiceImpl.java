package kg.attractor.exam_7.service.impl;

import jakarta.validation.Valid;
import kg.attractor.exam_7.dao.AccountDao;
import kg.attractor.exam_7.dao.TransactionDao;
import kg.attractor.exam_7.dao.UserDao;
import kg.attractor.exam_7.dto.transaction.TransactionViewDto;
import kg.attractor.exam_7.dto.transaction.TransactionDetailDto;
import kg.attractor.exam_7.dto.transaction.TransactionRollbackDto;
import kg.attractor.exam_7.dto.UserDto;
import kg.attractor.exam_7.exception.AccessDeniedException;
import kg.attractor.exam_7.exception.TransactionNotFoundException;
import kg.attractor.exam_7.model.User;
import kg.attractor.exam_7.service.AdminTransactionService;
import kg.attractor.exam_7.util.AuthAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminTransactionServiceImpl implements AdminTransactionService {

    private final TransactionDao transactionDao;
    private final AccountDao accountDao;
    private final UserDao userDao;
    private final AuthAdapter authAdapter;

    @Override
    public List<TransactionViewDto> getAllTransactions() {
        return transactionDao.findAll();
    }

    @Override
    public List<TransactionViewDto> getTransactionsRequiringApproval() {
        return transactionDao.findByRequiresApproval(true);
    }


    @Override
    public void approveTransaction(@Valid Integer transactionId) {
        TransactionDetailDto transaction = transactionDao.findDtoById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(String.valueOf(transactionId)));

        if (transaction.getFromAccount() != null) {
            BigDecimal fromBalance = accountDao.findById(transaction.getFromAccount().getId())
                    .orElseThrow(() -> new RuntimeException("Счет отправителя не найден"))
                    .getBalance();

            if (fromBalance.compareTo(transaction.getAmount()) < 0) {
                throw new AccessDeniedException("Недостаточно средств на счете отправителя");
            }

        }

        Integer adminId = authAdapter.getAuthId();
        User admin = userDao.getUserById(adminId)
                .orElseThrow(() -> new RuntimeException("Администратор не найден"));

        UserDto adminDto = UserDto.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .phoneNumber(admin.getPhoneNumber())
                .build();

        transaction.setStatus("COMPLETED");
        transaction.setApprovedBy(adminDto);
        transaction.setUpdatedAt(LocalDateTime.now());

        if (transaction.getFromAccount() != null) {
            BigDecimal fromAccountBalance = transaction.getFromAccount().getBalance();
            if (fromAccountBalance != null) {
                accountDao.updateBalance(
                        transaction.getFromAccount().getId(),
                        fromAccountBalance.subtract(transaction.getAmount())
                );
            } else {

                accountDao.updateBalance(
                        transaction.getFromAccount().getId(),
                        BigDecimal.ZERO.subtract(transaction.getAmount())
                );
            }
        }

        accountDao.updateBalance(
                transaction.getToAccount().getId(),
                transaction.getToAccount().getBalance().add(transaction.getAmount())
        );


        transactionDao.update(transaction);
    }

    @Transactional
    @Override
    public void rollbackTransaction(Integer transactionId) {
        TransactionDetailDto transaction = transactionDao.findDtoById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(String.valueOf(transactionId)));

        if (!"PENDING".equals(transaction.getStatus())) {
            throw new AccessDeniedException("Транзакция должна быть в статусе PENDING для отката");
        }

        if (!transaction.getApproved()) {
            throw new AccessDeniedException("Только транзакции, требующие подтверждения, могут быть откачены");
        }

        BigDecimal fromAccountBalance = accountDao.findById(transaction.getFromAccount().getId())
                .orElseThrow(() -> new RuntimeException("Счет отправителя не найден")).getBalance();

        BigDecimal toAccountBalance = accountDao.findById(transaction.getToAccount().getId())
                .orElseThrow(() -> new RuntimeException("Счет получателя не найден")).getBalance();

        if (toAccountBalance.compareTo(transaction.getAmount()) < 0) {
            throw new AccessDeniedException("Недостаточно средств на счете получателя для отката транзакции");
        }

        accountDao.updateBalance(
                transaction.getFromAccount().getId(),
                fromAccountBalance.add(transaction.getAmount())
        );

        accountDao.updateBalance(
                transaction.getToAccount().getId(),
                toAccountBalance.subtract(transaction.getAmount())
        );

        transaction.setStatus("ROLLED_BACK");
        transaction.setUpdatedAt(LocalDateTime.now());
        transactionDao.update(transaction);

        Integer adminId = authAdapter.getAuthId();
        User admin = userDao.getUserById(adminId)
                .orElseThrow(() -> new RuntimeException("Администратор не найден"));

        UserDto adminDto = UserDto.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .phoneNumber(admin.getPhoneNumber())
                .build();

        TransactionRollbackDto rollbackDto = TransactionRollbackDto.builder()
                .transaction(transaction)
                .rolledBackBy(adminDto)
                .createdAt(LocalDateTime.now())
                .build();

        transactionDao.createRollbackRecord(rollbackDto);
    }

    @Transactional
    @Override
    public void deleteTransaction(Integer rollbackId) {
        TransactionRollbackDto rollbackDto = transactionDao.findRollbackById(rollbackId)
                .orElseThrow(() -> new TransactionNotFoundException("Запись отката не найдена с ID: " + rollbackId));

        TransactionDetailDto transaction = rollbackDto.getTransaction();

        if (!"ROLLED_BACK".equals(transaction.getStatus())) {
            throw new AccessDeniedException("Невозможно удалить транзакцию: откат не был выполнен");
        }

        transaction.setStatus("DELETED");
        transaction.setUpdatedAt(LocalDateTime.now());

        transactionDao.update(transaction);

    }
}
