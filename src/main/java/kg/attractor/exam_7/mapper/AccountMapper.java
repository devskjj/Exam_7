package kg.attractor.exam_7.mapper;

import kg.attractor.exam_7.dto.account.AccountDto;
import kg.attractor.exam_7.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);

    Account toEntity(AccountDto accountDto);
}