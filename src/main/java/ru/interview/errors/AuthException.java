package ru.interview.errors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthException extends UsernameNotFoundException {

    public AuthException(String msg) {
        super(msg);
    }
}
