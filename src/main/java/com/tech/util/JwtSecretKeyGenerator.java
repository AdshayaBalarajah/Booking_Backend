package com.tech.util;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;

public class JwtSecretKeyGenerator {
    public static void main(String[] args) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256); // 256-bit key
        SecretKey secretKey = keyGen.generateKey();
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        System.out.println("Generated Secret Key: " + base64Key);
    }
}

