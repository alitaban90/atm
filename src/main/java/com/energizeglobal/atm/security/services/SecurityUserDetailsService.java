package com.energizeglobal.atm.security.services;

import com.energizeglobal.atm.security.repositories.AtmCardRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private final AtmCardRepository atmCardRepository;

    public SecurityUserDetailsService(AtmCardRepository atmCardRepository) {
        this.atmCardRepository = atmCardRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cardNumber)
            throws UsernameNotFoundException {
        return atmCardRepository.findByCardNumber(Long.parseLong(cardNumber))
                .orElseThrow(() -> new UsernameNotFoundException("User not present"));
    }
}
