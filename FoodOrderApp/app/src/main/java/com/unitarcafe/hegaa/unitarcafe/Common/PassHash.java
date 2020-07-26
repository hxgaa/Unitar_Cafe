package com.unitarcafe.hegaa.unitarcafe.Common;

//import android.util.Base64;

import java.security.MessageDigest;

public class PassHash {

    /** Computes a salted PBKDF2 hash of given plaintext password
     suitable for storing in a database.
     Empty passwords are not supported. */
    public static String getSaltedHash(String password) throws Exception {
//        byte[] pass = password.getBytes("UTF-8");
//        String hash = Base64.encodeToString(pass, Base64.DEFAULT);
        // store the salt with the password
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
//        return hash;
    }

}
