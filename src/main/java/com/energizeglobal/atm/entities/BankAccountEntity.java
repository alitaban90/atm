package com.energizeglobal.atm.entities;

import com.energizeglobal.atm.enums.BankAccountStatus;
import com.energizeglobal.atm.enums.BankAccountType;
import lombok.Data;

import javax.persistence.*;

/**
 * @author alitaban
 */
@Entity
@Table(name = "bank_account")
@Data
public class BankAccountEntity {

    @Id
    @Column(name = "account_number")
    Integer accountNumber;

    @Enumerated
    @Column(name = "account_type", nullable = false)
    BankAccountType bankAccountType;

    @Enumerated
    @Column(name = "status", nullable = false)
    BankAccountStatus bankAccountStatus;

    @Column(name = "customer_id", nullable = false)
    Integer customerId;
}
