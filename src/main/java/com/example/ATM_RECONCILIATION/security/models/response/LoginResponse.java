package com.example.ATM_RECONCILIATION.security.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import com.example.ATM_RECONCILIATION.security.models.UserModule;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
	
	 private String username;
	 private Collection<? extends GrantedAuthority> authorities;
	 private List<UserModule> userModules;
	 @JsonProperty("displayName")
	 private  String displayUser ;
	 private Integer loginStatus;
	 private String authenticationMode;

	 public LoginResponse(String username, Collection<? extends GrantedAuthority> authorities,
			 List<UserModule> userModules ,String displayUser, String authenticationMode) { 
		    this.username = username;
		    this.authorities = authorities; 
		    this.userModules = userModules;
		    this.displayUser = displayUser;
		    this.authenticationMode = authenticationMode;
	 }
	public LoginResponse(String displayUser,String username,
			Collection<? extends GrantedAuthority> authorities,
			List<UserModule> userModules,
			Integer loginStatus,
			String authenticationMode
			) {
		 this.displayUser = displayUser;
		 this.username = username;
		this.authorities = authorities;
		this.userModules=userModules;
	    this.authenticationMode = authenticationMode;
		this.loginStatus=loginStatus;
	}

	public LoginResponse(String displayUser, Collection<? extends GrantedAuthority> authorities,List<UserModule> userModules) {
		this.displayUser = displayUser;
		this.authorities = authorities;
		this.userModules=userModules;

	}
	
	public LoginResponse(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}

}
