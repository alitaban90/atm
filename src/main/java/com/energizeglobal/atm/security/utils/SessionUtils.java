package com.energizeglobal.atm.security.utils;

import com.energizeglobal.atm.security.entities.AtmCardEntity;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author alitaban
 */
public class SessionUtils {

    public static AtmCardEntity getCurrentAtmCard(){
        return (AtmCardEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Integer getCurrentCustomerId(){
        return getCurrentAtmCard().getCustomerId();
    }
}
