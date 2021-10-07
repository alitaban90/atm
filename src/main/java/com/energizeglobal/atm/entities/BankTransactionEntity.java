package com.energizeglobal.atm.entities;

import com.energizeglobal.atm.enums.TransactionType;
import lombok.Data;

import javax.persistence.*;

/**
 * @author alitaban
 */
@Entity
@Table(name = "bank_transaction")
@Data
public class BankTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "account_number", nullable = false)
    private Integer accountNumber;

    @Enumerated
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
