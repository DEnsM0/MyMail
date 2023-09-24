package com.email.persistence;

import java.util.Base64;

public class Encoder {
    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();

    public static String encode(String string){
        try {
            return encoder.encodeToString(string.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decode(String string){
        try {
            return new String(decoder.decode(string.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
