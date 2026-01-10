package kg.attractor.exam_7.mapper;

import kg.attractor.exam_7.dto.TransactionDisplayDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TransactionDaoMapper implements RowMapper<TransactionDisplayDto> {
    @Override
    public TransactionDisplayDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TransactionDisplayDto.builder()
                .id(rs.getInt("id"))
                .fromAccount(rs.getObject("from_account_id") != null ?
                        rs.getInt("from_account_id") : null)
                .toAccount(rs.getInt("to_account_id"))
                .amount(rs.getObject("amount", BigDecimal.class))
                .currency(rs.getString("currency_code"))
                .status(rs.getString("status"))
                .approved(rs.getBoolean("approved"))
                .approvedBy(rs.getObject("approved_by") != null ?
                        rs.getInt("approved_by") : null)
                .transactionType(rs.getString("transaction_type"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }
}
