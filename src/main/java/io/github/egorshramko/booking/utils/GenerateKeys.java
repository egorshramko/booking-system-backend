package io.github.egorshramko.booking.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

public class GenerateKeys {

    public static void main(String[] args) {
        System.out.println("access secret: " + generateKey());
        System.out.println("refresh secret: " + generateKey());
    }

    private static String generateKey() {
        return Encoders.BASE64.encode(Jwts.SIG.HS512.key().build().getEncoded());
    }
}
