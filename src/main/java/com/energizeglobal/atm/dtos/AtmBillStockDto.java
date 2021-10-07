package com.energizeglobal.atm.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author alitaban
 */
@Data
public class AtmBillStockDto {
    @Min(1)
    @NotNull
    private Integer billAmount;

    @Min(1)
    @NotNull
    private Integer stock;

    public AtmBillStockDto(@Min(1) @NotNull Integer billAmount, @Min(1) @NotNull Integer stock) {
        this.billAmount = billAmount;
        this.stock = stock;
    }
}
