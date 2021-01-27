package ru.interview.errors;

import lombok.Getter;

@Getter
public enum  BalanceError {
    FORMAT_ERROR(0,"Введите число в формате xxx.xx");

    private int id;
    private String description;

    BalanceError(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
