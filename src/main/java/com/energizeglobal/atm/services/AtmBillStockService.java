package com.energizeglobal.atm.services;

import com.energizeglobal.atm.dtos.AtmBillStockDto;
import com.energizeglobal.atm.entities.AtmBillStockEntity;
import com.energizeglobal.atm.enums.TransactionType;
import com.energizeglobal.atm.errors.BadRequestException;
import com.energizeglobal.atm.repositories.AtmBillStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author alitaban
 */
@Service
public class AtmBillStockService {

    private final AtmBillStockRepository atmBillStockRepository;

    public AtmBillStockService(AtmBillStockRepository atmBillStockRepository) {
        this.atmBillStockRepository = atmBillStockRepository;
    }

    public void updateBillStocks(List<AtmBillStockDto> atmBillStockDtos, TransactionType transactionType){
        atmBillStockDtos.forEach(atmBillStockDto -> {
            Optional<AtmBillStockEntity> optional = atmBillStockRepository.findById(atmBillStockDto.getBillAmount());
            int amount = 0;
            switch (transactionType){
                case WITHDRAWAL:
                    amount = -1 * atmBillStockDto.getStock();
                    break;
                case DEPOSIT:
                    amount = atmBillStockDto.getStock();
            }
            AtmBillStockEntity entity;
            if(optional.isPresent()){
                entity = optional.get();
                entity.setStock(entity.getStock() + amount);
            } else {
                entity = new AtmBillStockEntity();
                entity.setBillAmount(atmBillStockDto.getBillAmount());
                entity.setStock(amount);
            }
            if(entity.getStock() < 0) {
                throw new BadRequestException("stock not enough");
            }
            atmBillStockRepository.save(entity);
        });
    }

    List<AtmBillStockEntity> findAllAvailableBills(){
        return atmBillStockRepository.findAllByStockGreaterThan(0);
    }
}
