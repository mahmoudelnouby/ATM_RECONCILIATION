package com.example.ATM_RECONCILIATION.services;

import com.example.ATM_RECONCILIATION.security.models.MTSUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public interface GlobalService {


    public static String getCurrentUserName() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }

    };

    public static String getCurrentEmpOrg() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((MTSUser) principal).getEMP_ORG();

    };

    public static List < String > getCurrentUserPermissions() {

        List < String > perms = new ArrayList < String > ();
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(a -> {
            perms.add(a.getAuthority());
        });

        return perms;
    }

    @SuppressWarnings("unchecked")
    public static String getCurrentUserPermissionsQueryString() {

        List < GrantedAuthority > authorities = (List < GrantedAuthority > ) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String permissions = "";

        if (authorities != null) {
            for (int i = 0; i < authorities.size(); i++) {
                permissions += "'" + authorities.get(i).getAuthority() + "'";

                if (i != authorities.size() - 1)
                    permissions += ",";
            }
        }

        return permissions;
    }


    public static String decrypt(String encrypted) {
        final String key = "MTS@SECRET#KEYMA";
        final String initVector = "AMYEK#TERCES@STM";
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] original = cipher.doFinal(encryptedBytes);
            String originalString = new String(original);

            return new String(originalString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static String encrypt(String plainText) {
        final String key = "MTS@SECRET#KEYMA";
        final String initVector = "AMYEK#TERCES@STM";
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] paddedPlainText = padPlaintext(plainText);
            byte[] encryptedBytes = cipher.doFinal(paddedPlainText);
            String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);

            return encryptedString;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] padPlaintext(String plainText) {
        int paddingLength = 16 - (plainText.length() % 16);
        byte paddingByte = (byte) paddingLength;
        byte[] paddedText = new byte[plainText.length() + paddingLength];
        System.arraycopy(plainText.getBytes(), 0, paddedText, 0, plainText.length());
        for (int i = plainText.length(); i < paddedText.length; i++) {
            paddedText[i] = paddingByte;
        }
        return paddedText;
    }
}
