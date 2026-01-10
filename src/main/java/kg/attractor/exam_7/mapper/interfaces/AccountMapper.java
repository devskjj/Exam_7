package kg.attractor.exam_7.mapper.interfaces;

import kg.attractor.exam_7.dto.AccountDto;
import kg.attractor.exam_7.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AccountMapper.class})
public interface AccountMapper {
    AccountDto toDto(Account account);

    Account toEntity(AccountDto accountDto);
}