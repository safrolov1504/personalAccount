package ru.interview.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CodingPassword {
    public static String encodePassword(String passwordIn){
        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        return bcryptEncoder.encode(passwordIn);
    }
}
