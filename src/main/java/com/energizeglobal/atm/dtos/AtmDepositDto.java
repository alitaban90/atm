package com.energizeglobal.atm.dtos;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author alitaban
 */
@Data
public class AtmDepositDto {

    @NotNull
    private Integer accountNumber;

    @NotEmpty
    private @Valid List<AtmBillStockDto> bills;
}
