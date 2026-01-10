package kg.attractor.exam_7.mapper.interfaces;

import kg.attractor.exam_7.dto.TransactionDto;
import kg.attractor.exam_7.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, CurrencyDaoMapper.class, UserMapper.class})
public interface TransactionMapper {
    @Mapping(target = "approved", source = "approved")
    TransactionDto toDto(Transaction transaction);

    Transaction toEntity(TransactionDto transactionDto);
}