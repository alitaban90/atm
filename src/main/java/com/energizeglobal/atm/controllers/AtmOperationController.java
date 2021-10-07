package com.energizeglobal.atm.controllers;

import com.energizeglobal.atm.dtos.*;
import com.energizeglobal.atm.entities.BankAccountEntity;
import com.energizeglobal.atm.errors.BadRequestException;
import com.energizeglobal.atm.mappers.BankAccountMapper;
import com.energizeglobal.atm.security.utils.SessionUtils;
import com.energizeglobal.atm.services.BankAccountService;
import com.energizeglobal.atm.services.BankTransactionService;
import com.energizeglobal.atm.services.RegistrationService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AtmOperationController {

    private final BankAccountService bankAccountService;
    private final BankTransactionService bankTransactionService;
    private final RegistrationService registrationService;

    public AtmOperationController(BankAccountService bankAccountService, BankTransactionService bankTransactionService, RegistrationService registrationService) {
        this.bankAccountService = bankAccountService;
        this.bankTransactionService = bankTransactionService;
        this.registrationService = registrationService;
    }

    @PostMapping("/api/register")
    public RegistrationResponseDto register(@RequestBody @Valid RegistrationDto registrationDto){
        RegistrationResponseDto responseDto = registrationService.register(registrationDto);
        return responseDto;
    }

    @GetMapping("/api/accounts")
    public List<BankAccountDto> getAllActiveAccounts() {
        Integer customerId = SessionUtils.getCurrentCustomerId();
        List<BankAccountEntity> bankAccountEntities = bankAccountService.getAllActiveAccounts(customerId);
        return BankAccountMapper.INSTANCE.entitiesToDtos(bankAccountEntities);
    }

    @PostMapping("/api/transaction/deposit")
    public TransactionResultDto deposit(@RequestBody @Valid AtmDepositDto depositDto) {
        Integer customerId = SessionUtils.getCurrentCustomerId();
        bankTransactionService.deposit(customerId, depositDto);
        return new TransactionResultDto();
    }

    @PostMapping("/api/transaction/withdrawal")
    public TransactionResultDto withdrawal(@RequestBody @Valid AtmWithdrawalDto withdrawalDto) {
        Integer customerId = SessionUtils.getCurrentCustomerId();
        List<AtmBillStockDto> billStockDtoList = bankTransactionService
                .withdrawal(customerId, withdrawalDto);
        return new TransactionResultDto(billStockDtoList);
    }

    @GetMapping("/api/accounts/{accountNumber}/balance")
    public AccountBalanceDto getAccountBalance(@PathVariable Integer accountNumber) {
        Integer customerId = SessionUtils.getCurrentCustomerId();
        Integer accountBalance = bankTransactionService.getAccountBalance(customerId, accountNumber);
        return new AccountBalanceDto(accountNumber, accountBalance);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public Map<String, String> handleBadRequestExceptions(
            BadRequestException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public Map<String, String> handleBadRequestExceptions(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("errorMessage", ex.getMessage());
        errors.put("constraintName", ex.getConstraintName());
        return errors;
    }

}
