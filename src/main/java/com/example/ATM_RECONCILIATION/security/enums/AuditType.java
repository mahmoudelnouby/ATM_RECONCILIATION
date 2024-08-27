package com.example.ATM_RECONCILIATION.security.enums;

public enum AuditType {
	
	logout("logged out from"),
	login("logged in into"),
	loginSSO("navigate to");
	
	public final String label;
	
	 private AuditType(String label) {
        this.label = label;
	 }
	 
	 public String getLabel() {
		 return label;
	 }
}
