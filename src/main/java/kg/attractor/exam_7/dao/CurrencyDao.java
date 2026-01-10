package kg.attractor.exam_7.dao;

import kg.attractor.exam_7.dto.CurrencyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrencyDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<CurrencyDto> findByCode(String code) {
        String sql = "SELECT id, code FROM currencies WHERE code = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                CurrencyDto.builder().id(rs.getInt("id")).code(rs.getString("code")).build(), code).stream().findFirst();
    }
}
