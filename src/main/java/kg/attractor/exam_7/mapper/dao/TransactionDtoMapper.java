package kg.attractor.exam_7.mapper.dao;

import kg.attractor.exam_7.dto.account.AccountDto;
import kg.attractor.exam_7.dto.CurrencyDto;
import kg.attractor.exam_7.dto.transaction.TransactionDetailDto;
import kg.attractor.exam_7.dto.UserDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TransactionDtoMapper implements RowMapper<TransactionDetailDto> {
    @Override
    public TransactionDetailDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TransactionDetailDto.builder()
                .id(rs.getInt("id"))
                .fromAccount(rs.getObject("from_account_id") != null ?
                        AccountDto.builder().id(rs.getInt("from_account_id")).build() : null)
                .toAccount(AccountDto.builder().id(rs.getInt("to_account_id")).build())
                .amount(rs.getBigDecimal("amount"))
                .currency(CurrencyDto.builder().id(rs.getInt("currency_id")).build())
                .status(rs.getString("status"))
                .approved(rs.getBoolean("approved"))
                .approvedBy(rs.getObject("approved_by") != null ?
                        UserDto.builder().id(rs.getInt("approved_by")).build() : null)
                .transactionType(rs.getString("transaction_type"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }
}
