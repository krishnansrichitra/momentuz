package com.momentus.corefw.auth;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    public  static final String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";


    private static final String ALL =
            UPPERCASE + LOWERCASE + DIGITS + SPECIAL;

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePassword(int length) {
        if (length < 4) {
            throw new IllegalArgumentException(
                    "Password length must be at least 4"
            );
        }

        List<Character> passwordChars = new ArrayList<>();

        // Ensure minimum requirements
        passwordChars.add(randomChar(UPPERCASE));
        passwordChars.add(randomChar(DIGITS));
        passwordChars.add(randomChar(SPECIAL));

        // Fill remaining characters
        while (passwordChars.size() < length) {
            passwordChars.add(randomChar(ALL));
        }

        // Shuffle to avoid predictable positions
        Collections.shuffle(passwordChars, RANDOM);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }

    private static char randomChar(String source) {
        return source.charAt(RANDOM.nextInt(source.length()));
    }

    public static void main(String[] args) {
        System.out.println(generatePassword(12));
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");
    }


}
