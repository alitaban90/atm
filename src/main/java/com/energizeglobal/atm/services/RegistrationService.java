package com.energizeglobal.atm.services;

import com.energizeglobal.atm.dtos.RegistrationDto;
import com.energizeglobal.atm.dtos.RegistrationResponseDto;
import com.energizeglobal.atm.entities.BankAccountEntity;
import com.energizeglobal.atm.entities.BankCustomerEntity;
import com.energizeglobal.atm.enums.BankAccountStatus;
import com.energizeglobal.atm.repositories.BankAccountRepository;
import com.energizeglobal.atm.repositories.BankCustomerRepository;
import com.energizeglobal.atm.security.entities.AtmCardEntity;
import com.energizeglobal.atm.security.repositories.AtmCardRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

/**
 * @author alitaban
 */
@Service
public class RegistrationService {

    private final BankCustomerRepository bankCustomerRepository;
    private final BankAccountRepository bankAccountRepository;
    private final AtmCardRepository atmCardRepository;

    public RegistrationService(BankCustomerRepository bankCustomerRepository, BankAccountRepository bankAccountRepository, AtmCardRepository atmCardRepository) {
        this.bankCustomerRepository = bankCustomerRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.atmCardRepository = atmCardRepository;
    }

    public RegistrationResponseDto register(RegistrationDto registrationDto) {
        BankCustomerEntity customer = new BankCustomerEntity();
        customer.setFirstName(registrationDto.getFirstName());
        customer.setLastName(registrationDto.getLastName());
        customer.setPhoneNumber(registrationDto.getPhoneNumber());
        customer.setIdNumber(registrationDto.getIdNumber());
        customer = bankCustomerRepository.save(customer);
        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setCustomerId(customer.getCustomerId());
        bankAccount.setBankAccountType(registrationDto.getBankAccountType());
        bankAccount.setBankAccountStatus(BankAccountStatus.ACTIVE);
        bankAccount.setAccountNumber(generateAccountNumber(0));
        bankAccountRepository.save(bankAccount);
        AtmCardEntity atmCard = new AtmCardEntity();
        atmCard.setCustomerId(customer.getCustomerId());
        atmCard.setCardNumber(generateCardNumber(0));
        int pin = getRandomNumberInRange(1000, 9999);
        atmCard.setPin(new BCryptPasswordEncoder().encode(pin + ""));
        atmCard.setAccountNonLocked(true);
        atmCardRepository.save(atmCard);
        RegistrationResponseDto responseDto = new RegistrationResponseDto();
        responseDto.setAccountNumber(bankAccount.getAccountNumber());
        responseDto.setCardNumber(atmCard.getCardNumber());
        responseDto.setPin(pin);

        return responseDto;
    }

    private int generateAccountNumber(int i) {
        if (i == 1000000) {
            return -1;
        }
        Integer accountNumber = getRandomNumberInRange(100000, 999999);
        Optional optional = bankAccountRepository.findById(accountNumber);
        if (optional.isPresent()) {
            return generateAccountNumber(i + 1);
        }
        return accountNumber;
    }

    private long generateCardNumber(int i) {
        if (i == 1000000) {
            return -1;
        }
        long cardNumber = getRandomNumberInRange(100000000, 999999999);
        Optional optional = atmCardRepository.findById(cardNumber);
        if (optional.isPresent()) {
            return generateCardNumber(i + 1);
        }
        return cardNumber;
    }

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
