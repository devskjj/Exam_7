package kg.attractor.exam_7.dao;

import kg.attractor.exam_7.dto.account.AccountDto;
import kg.attractor.exam_7.mapper.dao.AccountDaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountDao {
    private final JdbcTemplate jdbcTemplate;
    private final AccountDaoMapper accountMapper;


    public Integer create(AccountDto dto) {
        String sql = """
                INSERT INTO accounts(user_id, account_number, currency_id, balance, created_at)
                VALUES (?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, dto.getUser().getId());
            ps.setString(2, dto.getAccountNumber());
            ps.setInt(3, dto.getCurrency().getId());
            ps.setBigDecimal(4, dto.getBalance());
            ps.setTimestamp(5, Timestamp.valueOf(dto.getCreatedAt()));
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            return ((Number) keys.get("id")).intValue();
        }

        return (Integer) Objects.requireNonNull(keyHolder.getKeys().get("ID"));
    }

    public Optional<AccountDto> findByAccountNumber(String number) {
        String sql = """
            SELECT a.id, a.user_id, a.account_number, a.currency_id, a.balance, a.created_at,
            c.code AS currency_code,
            u.username, u.phone_number
            FROM accounts a
            JOIN currencies c ON a.currency_id = c.id
            JOIN users u ON a.user_id = u.id
            WHERE a.account_number = ?
        """;
        return jdbcTemplate.query(sql, accountMapper, number).stream().findFirst();
    }

    public List<AccountDto> findAllByUserId(Integer userId) {
        String sql = """
            SELECT a.id, a.user_id, a.account_number, a.currency_id, a.balance, a.created_at,
            c.code AS currency_code,
            u.username, u.phone_number
            FROM accounts a
            JOIN currencies c ON a.currency_id = c.id
            JOIN users u ON a.user_id = u.id
            WHERE a.user_id = ?
        """;
        return jdbcTemplate.query(sql, accountMapper, userId);
    }

    public void updateBalance(Integer accountId, BigDecimal newBalance) {
        jdbcTemplate.update("UPDATE accounts SET balance = ? WHERE id = ?", newBalance, accountId);
    }

    public Optional<AccountDto> findById(Integer id) {
        String sql = """
            SELECT a.*, c.code AS currency_code, u.id AS user_id, u.username, u.phone_number
            FROM accounts a
            JOIN currencies c ON a.currency_id = c.id
            JOIN users u ON a.user_id = u.id
            WHERE a.id = ?
        """;
        return jdbcTemplate.query(sql, accountMapper, id).stream().findFirst();
    }
}
