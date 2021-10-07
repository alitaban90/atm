package com.energizeglobal.atm.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author alitaban
 */
@Entity
@Table(name = "bank_customer")
@Data
public class BankCustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "id_number", nullable = false)
    private String idNumber;
}
