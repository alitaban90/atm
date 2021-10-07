package com.energizeglobal.atm.security.services;

import com.energizeglobal.atm.security.entities.AtmCardEntity;
import com.energizeglobal.atm.security.repositories.AtmCardRepository;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author alitaban
 */
@Service
public class LoginAttemptService {

    private final static int MAX_ATTEMPT = 3;

    private final AtmCardRepository atmCardRepository;

    public LoginAttemptService(AtmCardRepository atmCardRepository) {
        this.atmCardRepository = atmCardRepository;
    }

    public void loginSucceed(AtmCardEntity atmCard) {
        if (atmCard.getLoginAttempts() != null && atmCard.getLoginAttempts() > 0) {
            atmCard.setLoginAttempts(0);
            atmCardRepository.save(atmCard);
        }
    }

    public void loginFailed(Long cardNumber) {
        Optional<AtmCardEntity> atmCardOptional = atmCardRepository.findByCardNumber(cardNumber);
        if (!atmCardOptional.isPresent()) {
            return;
        }
        AtmCardEntity atmCard = atmCardOptional.get();
        int attempts = atmCard.getLoginAttempts() == null ? 1 : atmCard.getLoginAttempts() + 1;
        atmCard.setLoginAttempts(attempts);

        if (attempts + 1 >
                MAX_ATTEMPT) {
            atmCard.setAccountNonLocked(false);
            atmCardRepository.save(atmCard);
            throw new LockedException("Too many invalid attempts. Account is locked!!");
        }
        atmCardRepository.save(atmCard);
    }

}
