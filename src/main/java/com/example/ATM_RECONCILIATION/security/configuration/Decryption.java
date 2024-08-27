package com.example.ATM_RECONCILIATION.security.configuration;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Decryption {

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
//    public String decrypt(String encrypted) throws InvalidAlgorithmParameterException, InvalidKeyException,
//            BadPaddingException, IllegalBlockSizeException, InvalidKeyException,
//                                                IllegalBlockSizeException, BadPaddingException {
//        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
//        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encrypted));
//        return new String(decryptedBytes);
//    }
}
