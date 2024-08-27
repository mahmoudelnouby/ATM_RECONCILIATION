package com.example.ATM_RECONCILIATION.security.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SC_USER_MODULE", schema = "bpm_adminstration")
public class UserModule {
	
	@Id
	private String MODULE_ID;
	private String USER_ID;
	private String USER_NAME;
	private String MODULE_NAME;
	private String MODULE_NAME_AR;
	private String PATH;
	

}
