package com.energizeglobal.atm.repositories;

import com.energizeglobal.atm.entities.AtmBillStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author alitaban
 */
@Repository
public interface AtmBillStockRepository extends JpaRepository<AtmBillStockEntity, Integer> {
    List<AtmBillStockEntity> findAllByStockGreaterThan(Integer minimumStock);
}
