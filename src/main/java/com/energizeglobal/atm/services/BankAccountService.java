package com.energizeglobal.atm.services;

import com.energizeglobal.atm.entities.BankAccountEntity;
import com.energizeglobal.atm.enums.BankAccountStatus;
import com.energizeglobal.atm.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author alitaban
 */
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccountEntity> getAllActiveAccounts(Integer customerId) {
        return bankAccountRepository.findAllByCustomerIdAndBankAccountStatus(customerId, BankAccountStatus.ACTIVE);
    }
}
