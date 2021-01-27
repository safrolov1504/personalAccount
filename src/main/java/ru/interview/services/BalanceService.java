package ru.interview.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.interview.models.Balance;
import ru.interview.repositories.BalanceRepository;

import java.math.BigDecimal;
import java.sql.SQLException;

@Slf4j
@Service
public class BalanceService {

    private BalanceRepository balanceRepository;

    @Autowired
    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public Balance getBalance(String userName)  {
        try {
            return balanceRepository.getBalance(userName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addBalance(String userName, BigDecimal changeBalance){
        try {
            return balanceRepository.addBalance(userName,changeBalance);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

