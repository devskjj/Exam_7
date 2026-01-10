package kg.attractor.exam_7.dao;


import kg.attractor.exam_7.dto.UserDto;
import kg.attractor.exam_7.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public Integer register(UserDto userDto) {
        String sql = """
                INSERT INTO users (username, password, phone_number, enabled, authority_id)
                VALUES (?, ?, ?, true, (SELECT id FROM roles WHERE role = 'USER'))
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userDto.getUsername());
            ps.setString(2, userDto.getPassword());
            ps.setString(3, userDto.getPhoneNumber());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM users WHERE phone_number = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, phoneNumber);
        return count != null && count > 0;
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM users WHERE phone_number = ?";
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), phoneNumber)));
    }

    public Optional<User> getUserById(Integer userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userId)));
    }
}
