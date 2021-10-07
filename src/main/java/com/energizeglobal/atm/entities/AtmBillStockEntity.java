package com.energizeglobal.atm.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author alitaban
 */
@Entity
@Table(name = "atm_bill_stock")
@Data
public class AtmBillStockEntity {

    @Id
    @Column(name = "bill_amount")
    private Integer billAmount;

    @Column(name = "stock", nullable = false)
    private Integer stock;
}
