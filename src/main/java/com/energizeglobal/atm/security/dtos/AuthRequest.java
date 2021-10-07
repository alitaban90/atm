package com.energizeglobal.atm.security.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthRequest {

    @NotNull
    private Long cardNumber;

    @NotNull
    private Integer pin;
}
