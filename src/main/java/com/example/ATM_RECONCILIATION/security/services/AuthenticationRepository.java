package com.example.ATM_RECONCILIATION.security.services;


import com.example.ATM_RECONCILIATION.security.models.request.SignInRequest;
import com.example.ATM_RECONCILIATION.security.models.response.JwtAuthenticationResponse;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface AuthenticationRepository {


	JwtAuthenticationResponse signin(SignInRequest request) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException;


}
