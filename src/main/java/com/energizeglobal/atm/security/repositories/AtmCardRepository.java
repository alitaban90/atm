package com.energizeglobal.atm.security.repositories;

import com.energizeglobal.atm.security.entities.AtmCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtmCardRepository extends JpaRepository<AtmCardEntity, Long> {
    Optional<AtmCardEntity> findByCardNumber(Long cardNumber);
}
