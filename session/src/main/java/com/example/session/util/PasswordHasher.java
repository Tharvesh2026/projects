package com.example.session.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static String hash(String password){
        return BCrypt.hashpw(
                password,
                BCrypt.gensalt(12)
        );
    }

    public static boolean verify(
            String plainPassword,
            String hashedPassword){

        return BCrypt.checkpw(
                plainPassword,
                hashedPassword
        );
    }
}