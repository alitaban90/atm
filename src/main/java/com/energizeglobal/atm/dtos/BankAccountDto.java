package com.energizeglobal.atm.dtos;

import com.energizeglobal.atm.enums.BankAccountStatus;
import com.energizeglobal.atm.enums.BankAccountType;
import lombok.Data;

/**
 * @author alitaban
 */
@Data
public class BankAccountDto {

    private Integer accountNumber;

    private BankAccountType bankAccountType;

    private BankAccountStatus bankAccountStatus;
}
