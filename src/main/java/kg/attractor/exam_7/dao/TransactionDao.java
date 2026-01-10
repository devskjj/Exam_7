package kg.attractor.exam_7.dao;

import kg.attractor.exam_7.dto.TransactionDisplayDto;
import kg.attractor.exam_7.dto.TransactionDto;
import kg.attractor.exam_7.dto.TransactionRollbackDto;
import kg.attractor.exam_7.mapper.TransactionDaoMapper;
import kg.attractor.exam_7.mapper.TransactionDtoMapper;
import kg.attractor.exam_7.mapper.TransactionRollbackDaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionDao {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionDtoMapper transactionDtoMapper;
    private final TransactionDaoMapper transactionMapper;
    private final TransactionRollbackDaoMapper transactionRollbackDaoMapper;

    public void create(TransactionDto dto) {
        jdbcTemplate.update("""
                            INSERT INTO transactions(
                                from_account_id, 
                                to_account_id, 
                                amount, 
                                currency_id, 
                                status, 
                                approved, 
                                approved_by,
                                transaction_type,
                                created_at,
                                updated_at
                            )
                            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """,
                dto.getFromAccount() != null ? dto.getFromAccount().getId() : null,
                dto.getToAccount().getId(),
                dto.getAmount(),
                dto.getCurrency().getId(),
                dto.getStatus(),
                dto.getTransactionType(),
                dto.getApproved(),
                dto.getApprovedBy() != null ? dto.getApprovedBy().getId() : null,
                dto.getCreatedAt(),
                dto.getUpdatedAt());
    }

    public void update(TransactionDto dto) {
        jdbcTemplate.update("""
                            UPDATE transactions
                            SET status = ?,
                                approved_by = ?,
                                updated_at = ?
                            WHERE id = ?
                        """,
                dto.getStatus(),
                dto.getApprovedBy() != null ? dto.getApprovedBy().getId() : null,
                dto.getUpdatedAt(),
                dto.getId());
    }

    public List<TransactionDisplayDto> findHistoryByAccountId(Integer accountId) {
        String sql = """
                    SELECT t.id, t.from_account_id, t.to_account_id, t.amount, 
                           c.code AS currency_code, t.status, t.approved,
                           t.approved_by, t.transaction_type, t.created_at, t.updated_at
                    FROM transactions t
                    JOIN currencies c ON t.currency_id = c.id
                    WHERE t.from_account_id = ? OR t.to_account_id = ?
                    ORDER BY t.created_at DESC
                """;
        return jdbcTemplate.query(sql, transactionMapper, accountId, accountId);
    }

    public List<TransactionDisplayDto> findAll() {
        String sql = """
                    SELECT t.id, t.from_account_id, t.to_account_id, t.amount, 
                           c.code AS currency_code, t.status, t.approved,
                           t.approved_by, t.transaction_type, t.created_at, t.updated_at
                    FROM transactions t
                    JOIN currencies c ON t.currency_id = c.id
                    ORDER BY t.created_at DESC
                """;
        return jdbcTemplate.query(sql, transactionMapper);
    }

    public List<TransactionDisplayDto> findByRequiresApproval(boolean requiresApproval) {
        String sql = """
                    SELECT t.id, t.from_account_id, t.to_account_id, t.amount,
                           c.code AS currency_code, t.status, t.approved,
                           t.approved_by, t.transaction_type, t.created_at, t.updated_at
                    FROM transactions t
                    JOIN currencies c ON t.currency_id = c.id
                    WHERE t.status = 'PENDING' AND t.approved = ?
                    ORDER BY t.created_at DESC
                """;
        return jdbcTemplate.query(sql, transactionMapper, requiresApproval);
    }

    public Optional<TransactionDto> findDtoById(Integer id) {
        String sql = """
                    SELECT t.id, t.from_account_id, t.to_account_id, t.amount, 
                           t.currency_id, t.status, t.approved,
                           t.approved_by, t.transaction_type, t.created_at, t.updated_at
                    FROM transactions t
                    WHERE t.id = ?
                """;

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, transactionDtoMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void createRollbackRecord(TransactionRollbackDto dto) {
        jdbcTemplate.update("""
                            INSERT INTO transaction_rollbacks(
                                transaction_id, 
                                rolled_back_by,
                                created_at
                            )
                            VALUES (?, ?, ?)
                        """,
                dto.getTransaction().getId(),
                dto.getRolledBackBy().getId(),
                dto.getCreatedAt());
    }

    public Optional<TransactionRollbackDto> findRollbackById(Integer id) {
        String sql = """
                    SELECT tr.id, tr.transaction_id, tr.rolled_back_by, tr.created_at
                    FROM transaction_rollbacks tr
                    WHERE tr.id = ?
                """;
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, transactionRollbackDaoMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
