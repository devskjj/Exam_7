package kg.attractor.exam_7.mapper;

import kg.attractor.exam_7.dto.transaction.TransactionRollbackDto;
import kg.attractor.exam_7.model.Rollback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TransactionMapper.class, UserMapper.class})
public interface TransactionRollbackMapper {
    TransactionRollbackDto toDto(Rollback rollback);

    Rollback toEntity(TransactionRollbackDto rollbackDto);
}
