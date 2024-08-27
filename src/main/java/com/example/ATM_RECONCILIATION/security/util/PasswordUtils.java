package com.example.ATM_RECONCILIATION.security.util;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class PasswordUtils {
	@Value("${secretKey}")
	public static String key;
	@Value("${secretInit}")
	public static String initVect;
	public static boolean isMatching(String firstPassword, String secondPassword) {
		return firstPassword.equals(secondPassword);
	}

	 public static String decrypt(String encrypted) {
//		Configured In Properties
//	        final String key = ;
//	        final String initVector = "";
	        IvParameterSpec ivParameterSpec = new IvParameterSpec(initVect.getBytes());
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
			//		Configured In Properties

//	        final String key = "MTS@SECRET#KEYMA";
//	        final String initVector = "AMYEK#TERCES@STM";
	        IvParameterSpec ivParameterSpec = new IvParameterSpec(initVect.getBytes());
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
