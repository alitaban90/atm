package com.energizeglobal.atm.repositories;

import com.energizeglobal.atm.entities.BankCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author alitaban
 */
@Repository
public interface BankCustomerRepository extends JpaRepository<BankCustomerEntity, Integer> {
}
