package com.clevertap.utils;

import java.util.List;
import java.util.Random;

public class Data {


    private static final String ALPHA_NUMERIC_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(String defaultStr, int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return defaultStr + builder.toString();
    }

    public static Object getRandomElement(List<Object> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public static int randomNuber(int bound){
        Random random = new Random();
        return random.nextInt(bound);
    }

}
