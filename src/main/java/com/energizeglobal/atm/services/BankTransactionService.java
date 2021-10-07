package com.energizeglobal.atm.services;

import com.energizeglobal.atm.dtos.AtmBillStockDto;
import com.energizeglobal.atm.dtos.AtmDepositDto;
import com.energizeglobal.atm.dtos.AtmWithdrawalDto;
import com.energizeglobal.atm.entities.AtmBillStockEntity;
import com.energizeglobal.atm.entities.BankAccountEntity;
import com.energizeglobal.atm.entities.BankTransactionEntity;
import com.energizeglobal.atm.enums.TransactionType;
import com.energizeglobal.atm.errors.BadRequestException;
import com.energizeglobal.atm.repositories.BankTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author alitaban
 */
@Service
public class BankTransactionService {

    private final BankTransactionRepository bankTransactionRepository;
    private final BankAccountService bankAccountService;
    private final AtmBillStockService atmBillStockService;

    public BankTransactionService(BankTransactionRepository bankTransactionRepository, BankAccountService bankAccountService, AtmBillStockService atmBillStockService) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.bankAccountService = bankAccountService;
        this.atmBillStockService = atmBillStockService;
    }

    public void deposit(Integer customerId, AtmDepositDto atmDepositDto) {
        List<BankAccountEntity> bankAccountEntities = bankAccountService.getAllActiveAccounts(customerId);
        if (bankAccountEntities.stream()
                .noneMatch(bankAccountEntity -> bankAccountEntity.getAccountNumber().equals(atmDepositDto.getAccountNumber()))) {
            throw new BadRequestException("account number not valid");
        }
        atmBillStockService.updateBillStocks(atmDepositDto.getBills(), TransactionType.DEPOSIT);
        int amount = atmDepositDto.getBills().stream().map(billStock -> billStock.getStock() * billStock.getBillAmount())
                .reduce(Integer::sum).get();
        BankTransactionEntity bankTransactionEntity = new BankTransactionEntity();
        bankTransactionEntity.setAccountNumber(atmDepositDto.getAccountNumber());
        bankTransactionEntity.setTransactionType(TransactionType.DEPOSIT);
        bankTransactionEntity.setAmount(amount);
        bankTransactionRepository.save(bankTransactionEntity);
    }

    public List<AtmBillStockDto> withdrawal(Integer customerId, AtmWithdrawalDto dto) {
        List<BankAccountEntity> bankAccountEntities = bankAccountService.getAllActiveAccounts(customerId);
        if (bankAccountEntities.stream()
                .noneMatch(bankAccountEntity -> bankAccountEntity.getAccountNumber().equals(dto.getAccountNumber()))) {
            throw new BadRequestException("account number not valid");
        }
        int accountBalance = getAccountBalance(dto.getAccountNumber());
        if (accountBalance < dto.getAmount()) {
            throw new BadRequestException("balance not enough");
        }

        List<AtmBillStockEntity> availableBills = atmBillStockService.findAllAvailableBills();
        List<AtmBillStockDto> payingBills = minBillChange(availableBills, dto.getAmount());
        atmBillStockService.updateBillStocks(payingBills, TransactionType.WITHDRAWAL);

        BankTransactionEntity bankTransactionEntity = new BankTransactionEntity();
        bankTransactionEntity.setAccountNumber(dto.getAccountNumber());
        bankTransactionEntity.setTransactionType(TransactionType.DEPOSIT);
        bankTransactionEntity.setAmount(dto.getAmount() * -1);
        bankTransactionRepository.save(bankTransactionEntity);

        return payingBills;
    }

    private List<AtmBillStockDto> minBillChange(List<AtmBillStockEntity> availableBills, Integer amount) {
        int[] minBills = new int[amount + 1];
        minBills[0] = 0;
        for (int i = 1; i <= amount; i++) {
            minBills[i] = Integer.MAX_VALUE;
        }
        HashMap<Integer, Integer>[] minBillsData = new HashMap[amount + 1];
        List<AtmBillStockEntity> availableBillsSorted = availableBills.stream()
                .sorted(Comparator.comparing(AtmBillStockEntity::getBillAmount)).collect(Collectors.toList());
        for (AtmBillStockEntity billStock : availableBillsSorted) {
            for (int i = 0; i <= amount; i++) {
                int billAmount = billStock.getBillAmount();
                int stock = billStock.getStock();
                if ((i - billAmount) >= 0) {
                    if (minBills[i - billAmount] < Integer.MAX_VALUE && minBills[i - billAmount] + 1 < minBills[i]) {
                        HashMap<Integer, Integer> preBillsData = minBillsData[i - billAmount];
                        int preBillCount = 0;
                        if (preBillsData != null) {
                            preBillCount = preBillsData.getOrDefault(billAmount, 0);
                        }
                        if (preBillCount + 1 <= stock) {
                            minBills[i] = minBills[i - billAmount] + 1;
                            HashMap<Integer, Integer> billsData;
                            if (preBillsData != null) {
                                billsData = new HashMap<>(preBillsData);
                            } else {
                                billsData = new HashMap<>();
                            }
                            billsData.put(billAmount, preBillCount + 1);
                            minBillsData[i] = billsData;
                        }
                    }
                }
            }
        }
        if (minBills[amount] == Integer.MAX_VALUE) {
            throw new BadRequestException("bills shortage");
        }
        List<AtmBillStockDto> billStockDtoList = new ArrayList<>();
        minBillsData[amount].forEach((k, v) -> billStockDtoList.add(new AtmBillStockDto(k, v)));
        return billStockDtoList;
    }

    public Integer getAccountBalance(Integer customerId, Integer accountNumber) {
        List<BankAccountEntity> bankAccountEntities = bankAccountService.getAllActiveAccounts(customerId);
        if (bankAccountEntities.stream()
                .noneMatch(bankAccountEntity -> bankAccountEntity.getAccountNumber().equals(accountNumber))) {
            throw new BadRequestException("account number not valid");
        }
        return getAccountBalance(accountNumber);
    }

    private Integer getAccountBalance(Integer accountNumber) {
        return bankTransactionRepository.sumByAccountNumber(accountNumber);
    }
}
