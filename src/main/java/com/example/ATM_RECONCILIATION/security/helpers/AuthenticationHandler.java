package com.example.ATM_RECONCILIATION.security.helpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import com.example.ATM_RECONCILIATION.security.enums.SecurityResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Data
public class AuthenticationHandler implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);
	
	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
		logger.error("Unauthorized error: {}", e.getMessage());
		res.sendError(HttpServletResponse.SC_UNAUTHORIZED, SecurityResponseMessage.Unauthorized_Request.getMessage());
	}

}
