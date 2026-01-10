package kg.attractor.exam_7.mapper;

import kg.attractor.exam_7.dto.TransactionDto;
import kg.attractor.exam_7.dto.TransactionRollbackDto;
import kg.attractor.exam_7.dto.UserDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TransactionRollbackDaoMapper implements RowMapper<TransactionRollbackDto> {
    @Override
    public TransactionRollbackDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TransactionRollbackDto.builder()
                .id(rs.getInt("id"))
                .transaction(TransactionDto.builder().id(rs.getInt("transaction_id")).build())
                .rolledBackBy(UserDto.builder().id(rs.getInt("rolled_back_by")).build())
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}