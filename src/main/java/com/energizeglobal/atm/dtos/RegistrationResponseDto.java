package com.energizeglobal.atm.dtos;

import lombok.Data;

/**
 * @author alitaban
 */
@Data
public class RegistrationResponseDto {

    private Long cardNumber;
    private Integer accountNumber;
    private Integer pin;
}
