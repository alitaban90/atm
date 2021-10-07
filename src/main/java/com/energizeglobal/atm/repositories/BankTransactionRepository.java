package com.energizeglobal.atm.repositories;

import com.energizeglobal.atm.entities.BankTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author alitaban
 */
@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransactionEntity, Integer> {

    @Query("select sum(t.amount) from BankTransactionEntity t where t.accountNumber = ?1")
    Integer sumByAccountNumber(Integer accountNumber);
}
