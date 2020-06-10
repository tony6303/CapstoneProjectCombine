package com.example.myapplication;

import java.security.MessageDigest;

public class SHA256 {
    public String sha256(String password){
        String sha;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[]=md.digest();
            StringBuffer sb =new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            sha = sb.toString();
        }catch (Exception e){
            System.out.println("Encrypt Error - NoSuchAlgorithmException");
            sha = null;
        }
        return sha;
    }

}
