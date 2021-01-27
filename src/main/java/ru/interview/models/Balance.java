package ru.interview.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Setter
@Getter
@AllArgsConstructor
public class Balance {
    private String firstName;
    private String secondName;
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance.divide(new BigDecimal(1),2, RoundingMode.HALF_UP);
    }
}
