package kg.attractor.exam_7.mapper.dao;

import kg.attractor.exam_7.dto.account.AccountDto;
import kg.attractor.exam_7.dto.CurrencyDto;
import kg.attractor.exam_7.dto.UserDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AccountDaoMapper implements RowMapper<AccountDto> {
    @Override
    public AccountDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AccountDto.builder()
                .id(rs.getInt("id"))
                .accountNumber(rs.getString("account_number"))
                .balance(rs.getBigDecimal("balance"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .currency(mapCurrency(rs))
                .user(mapUser(rs))
                .build();
    }

    private CurrencyDto mapCurrency(ResultSet rs) throws SQLException {
        return CurrencyDto.builder()
                .id(rs.getInt("currency_id"))
                .code(rs.getString("currency_code"))
                .build();
    }

    private UserDto mapUser(ResultSet rs) throws SQLException {
        return UserDto.builder()
                .id(rs.getInt("user_id"))
                .username(rs.getString("username"))
                .phoneNumber(rs.getString("phone_number"))
                .build();
    }
}
