package com.energizeglobal.atm.dtos;

import com.energizeglobal.atm.enums.BankAccountType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author alitaban
 */
@Data
public class RegistrationDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String idNumber;

    @NotNull
    private BankAccountType bankAccountType;
}
