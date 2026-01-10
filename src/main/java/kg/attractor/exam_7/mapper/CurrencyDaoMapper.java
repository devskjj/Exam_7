package kg.attractor.exam_7.mapper;

import kg.attractor.exam_7.dto.CurrencyDto;
import kg.attractor.exam_7.model.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyDaoMapper {
    CurrencyDto toDto(Currency currency);

    Currency toEntity(CurrencyDto currencyDto);
}
