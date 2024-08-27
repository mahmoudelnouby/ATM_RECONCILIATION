package com.example.ATM_RECONCILIATION.security.services;

import com.example.ATM_RECONCILIATION.security.models.WLSConfiguration;
import com.example.ATM_RECONCILIATION.security.repos.SystemConfigurationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigurationService {

	@Autowired
	SystemConfigurationDAO dao;
	
	
	public WLSConfiguration getWLSConfig() {
		return dao.getWLSConfig();
	}
	
}
