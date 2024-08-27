package com.example.ATM_RECONCILIATION.security.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table(name="SC_USER_PERMISSION", schema = "bpm_adminstration")
public class UserPermission{
	
	@Id
	private Long PERMISSION_ID;
	private Long USER_ID;
	private Long MODULE_ID;
	private String USER_NAME;
	private String PERMISSION_NAME;
	
}
