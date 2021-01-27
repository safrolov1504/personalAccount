package ru.interview.errors;

import lombok.Getter;

@Getter
public enum ErrorUser {
    OK(0,"Все ОК. Пользователь добавлен"),
    LOGIN_NOT_UNIQ(1,"Пользователь с таким логином уже существует"),
    TECHNICAL_ERROR(2, "Tехническая ошибка"),
    NO_USER(3,"Пользователь не существует"),
    NOT_CORRECT_PASSWORD(4,"Пароль не верен"),
    NO_INFORMATION(5,"Не все обязательные поля заполнены");

    private int id;
    private String description;

    ErrorUser(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
