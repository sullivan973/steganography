package com.sandbox;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public static String generateHash(String password) {
        StringBuilder passwordHash = new StringBuilder();

        try {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] hashedBytes = sha.digest(password.getBytes());
        char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f' };
        for (int idx = 0; idx < hashedBytes.length; ++idx) {
        byte b = hashedBytes[idx];
        passwordHash.append(digits[(b & 0xf0) >> 4]);
        passwordHash.append(digits[b & 0x0f]);
        }
        } catch (NoSuchAlgorithmException e) {
        System.out.println("Error occured while hashing password");
        }

        return passwordHash.toString();
        }