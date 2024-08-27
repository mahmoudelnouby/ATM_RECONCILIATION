package com.example.ATM_RECONCILIATION.security.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SC_ROLES", schema = "bpm_adminstration")
public class Role {

	@Id
	private Long ROLE_ID;
	private String ROLE_NAME;
	private Long PARENT_ROLE_ID;
	private String LAST_MODIFIED_BY;
	
}
