package com.example.ATM_RECONCILIATION.security.enums;

public enum SecurityResponseMessage {
	  
	Unauthorized_Request("Unauthorized request"),
	Invalid_Referer("Invalid referer"),
	Invalid_Credentials("Invalid username or password"),
	Logout_Failed( "Failed To Log out"),
	Logout_Success("Logged out Successfully"),
	Missing_Username("Missing username"),
	Password_Changed_Success("Password changed successfully"),
	Password_Changed_Failed("Password change failed"),
	Passwords_Not_Matched("Passwords not match"),
	Many_Requests("Too many requests !"),
	Passwords_SAME("New password must not be equal to the old password"),
	
	User_Locked("User is locked"),
	User_Authenticated_Successfully("User authenticated successfully"),
	User_Already_Logged_In("User is already logged in"),
	User_Not_Authenticated("User Not Authenticated"),
	User_Not_Found("User does not exist"),
	
	;
	
	private final String message;

	SecurityResponseMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	public String getMessageByUsername(String username) {
		return message.replace("User", username);
	}
}
