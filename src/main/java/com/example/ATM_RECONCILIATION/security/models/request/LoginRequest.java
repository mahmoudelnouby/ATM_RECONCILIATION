package com.example.ATM_RECONCILIATION.security.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequest {
	
	@JsonProperty("username")
	private String username;
	
	@JsonProperty("password")
	private String password;
	
}
