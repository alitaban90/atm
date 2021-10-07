package com.energizeglobal.atm.security.controllers;

import com.energizeglobal.atm.security.dtos.AuthRequest;
import com.energizeglobal.atm.security.dtos.AuthResponse;
import com.energizeglobal.atm.security.utils.JwtTokenUtil;
import com.energizeglobal.atm.security.services.SecurityUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, SecurityUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @RequestMapping(value = "/api/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid AuthRequest authenticationRequest) throws Exception {

        Authentication authentication = authenticate(authenticationRequest.getCardNumber(), authenticationRequest.getPin());

        final String token = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    private Authentication authenticate(Long cardNumber, Integer pin) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(cardNumber, pin));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
