package com.energizeglobal.atm.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author alitaban
 */
@Data
public class AtmWithdrawalDto {
    @NotNull
    private Integer accountNumber;

    @Min(1L)
    @NotNull
    private Integer amount;
}
