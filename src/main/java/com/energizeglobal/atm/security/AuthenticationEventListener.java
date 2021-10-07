package com.energizeglobal.atm.security;

import com.energizeglobal.atm.security.entities.AtmCardEntity;
import com.energizeglobal.atm.security.services.LoginAttemptService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * @author alitaban
 */
@Component
public class AuthenticationEventListener {

    private final LoginAttemptService loginAttemptService;

    public AuthenticationEventListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {
        Long cardNumber = (Long) event.getAuthentication().getPrincipal();

        loginAttemptService.loginFailed(cardNumber);
    }

    @EventListener
    public void authenticationSucceed(AuthenticationSuccessEvent event){
        AtmCardEntity atmCard = (AtmCardEntity) event.getAuthentication().getPrincipal();

        loginAttemptService.loginSucceed(atmCard);
    }
}
