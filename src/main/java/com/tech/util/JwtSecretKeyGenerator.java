package com.tech.util;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;

public class JwtSecretKeyGenerator {
    public static void main(String[] args) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
        keyGen.init(512); // 512-bit key
        SecretKey secretKey = keyGen.generateKey();

        // Encode the key as a Base64 string
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        System.out.println("Generated 512-bit Base64 Encoded Secret Key: " + base64Key);
    }
}

