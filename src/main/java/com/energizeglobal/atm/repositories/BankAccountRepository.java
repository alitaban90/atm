package com.energizeglobal.atm.repositories;

import com.energizeglobal.atm.entities.BankAccountEntity;
import com.energizeglobal.atm.enums.BankAccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author alitaban
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Integer> {

    List<BankAccountEntity> findAllByCustomerIdAndBankAccountStatus(Integer customerId, BankAccountStatus bankAccountStatus);
}
