package com.energizeglobal.atm.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author alitaban
 */
@Data
@NoArgsConstructor
public class TransactionResultDto {
    private String message = "succeed";

    private List<AtmBillStockDto> bills;

    public TransactionResultDto(List<AtmBillStockDto> bills) {
        this.bills = bills;
    }
}
