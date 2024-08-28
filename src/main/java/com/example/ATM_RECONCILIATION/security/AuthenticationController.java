package com.example.ATM_RECONCILIATION.security;


import com.example.ATM_RECONCILIATION.GlobalEnums.ResponseMessage;
import com.example.ATM_RECONCILIATION.payload.response.Response;
import com.example.ATM_RECONCILIATION.security.models.request.SignInRequest;
import com.example.ATM_RECONCILIATION.security.models.request.changePasswordRequest;
import com.example.ATM_RECONCILIATION.security.models.response.JwtAuthenticationResponse;
import com.example.ATM_RECONCILIATION.security.services.UserService;
import com.example.ATM_RECONCILIATION.security.services.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import oracle.net.ano.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private final AuthenticationServiceImpl authenticationService;
    private final UserService userService;
//    @PostMapping("signup")
//    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
//        return ResponseEntity.ok(authenticationService.signup(request));
//    }

    @PostMapping("signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @PostMapping("changePassword")
    public ResponseEntity<?> changePassword(@RequestBody changePasswordRequest request){
        return ResponseEntity.ok(userService.changePass(request));
    }
    @GetMapping("logout")

    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
            Response resp = new Response();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
                SecurityContextHolder.clearContext();
                resp.setCustomerMessage("Success");
                resp.setDeveloperMessage(ResponseMessage.Developer_Message.getMessageByApi("Logout"));
                resp.setStatusCode(200);
                resp.setBody("LogOut Success ");
                return new ResponseEntity<>(resp, new HttpHeaders(), HttpStatus.OK);

            } else {

                resp.setCustomerMessage("Fail");
                resp.setStatusCode(500);
                resp.setCustomerMessage("Logout Failed");
                resp.setDeveloperMessage(ResponseMessage.Logout_Failed.getMessageByApi("Logout"));
                return new ResponseEntity<>(resp, new HttpHeaders(), HttpStatus.OK);
                // You can redirect wherever you want, but generally it's a good practice to
                // show login screen again.
            }}
}