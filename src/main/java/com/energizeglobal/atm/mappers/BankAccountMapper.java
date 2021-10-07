package com.energizeglobal.atm.mappers;

import com.energizeglobal.atm.dtos.BankAccountDto;
import com.energizeglobal.atm.entities.BankAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author alitaban
 */
@Mapper
public interface BankAccountMapper {
    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);

    List<BankAccountDto> entitiesToDtos(List<BankAccountEntity> entities);
}
