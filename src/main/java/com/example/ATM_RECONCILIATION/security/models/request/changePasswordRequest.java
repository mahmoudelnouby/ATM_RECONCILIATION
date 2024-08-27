package com.example.ATM_RECONCILIATION.security.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class changePasswordRequest {
		
		@JsonProperty("oldPassword")
		private String oldPassword;
		
		@JsonProperty("newPassword")
		private String newPassword;

}
