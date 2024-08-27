package com.example.ATM_RECONCILIATION.security.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "WEB_LOGIC_CONF", schema = "bpm_adminstration")
public class WLSConfiguration {

	@Id
	private String SERVER_IP;
	private String SERVER_PORT;
	private String SERVER_USERNAME;
	private String SERVER_PASSWORD;
	private String MAINSCREEN_IP;
	private String MAINSCREEN_PORT;
	private String IS_SSL;
	private String PROTOCOL;
	
}
