package com.example.ATM_RECONCILIATION.security.services.impl;

import com.example.ATM_RECONCILIATION.security.configuration.EncryptionAlgo;
import com.example.ATM_RECONCILIATION.security.models.User;
import com.example.ATM_RECONCILIATION.security.models.UserModule;
import com.example.ATM_RECONCILIATION.security.models.UserPermission;
import com.example.ATM_RECONCILIATION.security.models.request.LoginRequest;
import com.example.ATM_RECONCILIATION.security.models.request.SignInRequest;
import com.example.ATM_RECONCILIATION.security.models.response.JwtAuthenticationResponse;
import com.example.ATM_RECONCILIATION.security.models.response.LoginResponse;
import com.example.ATM_RECONCILIATION.security.repos.*;
import com.example.ATM_RECONCILIATION.security.services.JwtService;
import com.example.ATM_RECONCILIATION.security.services.SecurityParameterService;
import com.example.ATM_RECONCILIATION.security.services.SystemConfigurationService;
import com.example.ATM_RECONCILIATION.security.services.UserDetailsServiceImpl;
import com.example.ATM_RECONCILIATION.security.util.PasswordUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Data
public class AuthenticationServiceImpl implements AuthenticationRepository{
	
	
	@Value("${mode}")
	private String mode;

	@Value("${user}")
	private String user;
	
    @Value("${cookie.username}")
    private String cookieUsername;
    
    @Value("${app.auth.changePassword.infirsttime}")
    private Boolean appAuthChangePasswordInFirstTime;
	@Value("${ldap.flag}")
	private Integer ldapFlag;
	
	static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Autowired
	private final AuthenticationManager authenticationManager;

	@Autowired
	private SystemConfigurationService service;
	
	@Autowired
    SecurityParameterService securityParameterService;

	@Autowired
	SystemConfigurationDAO dao;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse httpResponse;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private UserPermissionsRepository userPermissionRepo;

	@Autowired
	private UsersRepository usersRepo;
	@Autowired
	UserModulesRepository userModuleRepo;
	@Autowired
	private final JwtService jwtService;

	@Autowired
	UserRepo userRepo;



	public String getUsernameFromCookies() {
		  if (mode.equals("dev")) {
		    return user;
		  } else {
		    request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
		      .getRequest();
		    Cookie[] cookies = request.getCookies();
		    if (cookies != null) {
		      for (Cookie cookie: cookies) {
		        if (cookie.getName().equals(cookieUsername)) {
		          String userName = Objects.requireNonNull(PasswordUtils.decrypt(cookie.getValue())).trim();
		          cookie.setHttpOnly(true);
		          return userName;
		        }
		      }
		    }
		    return null;
		  }
	}

	@Override
	public JwtAuthenticationResponse signin(SignInRequest request) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
//		int ldapFlag=securityConfigRepo.FindLdapFlagByModuleId("System_Administration");
		if(ldapFlag==1)
		{
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			EncryptionAlgo encryptionAlgo = new EncryptionAlgo();
//        String encryptedPassword = encryptionAlgo.encrypt(request.getPassword());
//        String encrypted=passwordEncoder.encode(request.getPassword());
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
			);
			var user =usersRepo.findByUserName(request.getUsername()).
					orElseThrow(()-> new IllegalArgumentException("Invalid UserName"));

			// authentication with database
//        var user = userRepository.authenticate(request.getUsername(),request.getPassword())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid UserName or password."));
			var jwt = jwtService.generateToken(user);
			Set<UserPermission> userPermissionSet= userPermissionRepo.getUserPermissionsByUserName(request.getUsername(),Integer.toUnsignedLong(3));
			return JwtAuthenticationResponse.builder().token(jwt).displayname(user.getDisplayName()).userPermissionSet(userPermissionSet).build();
		}
		else {
			EncryptionAlgo encryptionAlgo = new EncryptionAlgo();
//            String encryptedPassword = encryptionAlgo.encrypt(request.getPassword());
//            String encrypted=passwordEncoder.encode(request.getPassword());
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//            );
//            var user =userRepository.findByUsername(request.getUsername()).
////                    orElseThrow(()-> new IllegalArgumentException("Invalid UserName"));

			// authentication with database
			var user = userRepo.authenticate(request.getUsername(),request.getPassword())
					.orElseThrow(() -> new IllegalArgumentException("Invalid UserName or password."));
			var jwt = jwtService.generateToken(user);
			Set<UserPermission> userPermissionSet= userPermissionRepo.getUserPermissionsByUserName(request.getUsername(),Integer.toUnsignedLong(3));
			return JwtAuthenticationResponse.builder().token(jwt).displayname(user.getDisplayName()).userPermissionSet(userPermissionSet).build();
		}
	}
}
