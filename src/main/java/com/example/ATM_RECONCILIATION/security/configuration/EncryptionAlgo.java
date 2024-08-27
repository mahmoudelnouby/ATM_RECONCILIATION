package com.example.ATM_RECONCILIATION.security.configuration;


import com.example.ATM_RECONCILIATION.security.models.SingleTonParamter;
import lombok.Getter;
import lombok.Setter;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Setter
@Getter
public class EncryptionAlgo {
  //  private static  String SECRET_KEY_1 = "ssdkF$HUy2A#D%kd";
  //  private static  String SECRET_KEY_2 = "weJiSEvR5yAC5ftB";

  private String secretKey1;
  private String secretKey2;
  private String algoType;
  private String algoName;
  private IvParameterSpec ivParameterSpec;
  private SecretKeySpec secretKeySpec;
  private Cipher cipher;

  public EncryptionAlgo() throws NoSuchPaddingException, NoSuchAlgorithmException {
    try {
      secretKey1 = SingleTonParamter.getInstance().getEncKey1();
      secretKey2 = SingleTonParamter.getInstance().getEncKey2();
      algoType = SingleTonParamter.getInstance().getAlgoType();
      algoName = SingleTonParamter.getInstance().getAlgoName();
      this.ivParameterSpec = new IvParameterSpec(secretKey1.getBytes("UTF-8"));
      this.secretKeySpec = new SecretKeySpec(secretKey2.getBytes("UTF-8"), algoType);
      this.cipher = Cipher.getInstance(algoName);
    } catch (Exception e) {
    }
  }

  public String encrypt(final String toBeEncrypt)
      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
          InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
    this.cipher.init(1, this.secretKeySpec, this.ivParameterSpec);
    final byte[] encrypted = this.cipher.doFinal(toBeEncrypt.getBytes());
    return Base64.getEncoder().encodeToString(encrypted);
  }

  public String decrypt(final String encrypted)
      throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException,
          IllegalBlockSizeException {
    this.cipher.init(2, this.secretKeySpec, this.ivParameterSpec);
    final byte[] decryptedBytes = this.cipher.doFinal(Base64.getDecoder().decode(encrypted));
    return new String(decryptedBytes);
  }
}
