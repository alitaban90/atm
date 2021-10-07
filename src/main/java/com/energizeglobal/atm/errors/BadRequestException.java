package com.energizeglobal.atm.errors;

import lombok.Data;

/**
 * @author alitaban
 */
@Data
public class BadRequestException extends RuntimeException {

    public BadRequestException(String msg) {
        super(msg);
    }
}
