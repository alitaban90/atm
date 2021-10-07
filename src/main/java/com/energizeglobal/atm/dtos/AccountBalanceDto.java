package com.energizeglobal.atm.dtos;

import lombok.Data;

/**
 * @author alitaban
 */
@Data
public class AccountBalanceDto {
    private Integer accountNumber;
    private Integer accountBalance;

    public AccountBalanceDto(Integer accountNumber, Integer accountBalance) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }
}
