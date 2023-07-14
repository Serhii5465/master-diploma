package com.example.iotvandergraaf.presenter.login;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPass {

    private static final String TAG = "EncryptPass";

    public static String getSecurePassword(String passwordToHash,String salt){
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());

            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();

            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }


    public static String getRandomString(){
       return RandomStringUtils.randomAlphabetic(25).toLowerCase();
    }
}