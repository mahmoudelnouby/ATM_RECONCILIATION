package com.example.ATM_RECONCILIATION.security.services;

import jakarta.transaction.Transactional;
import com.example.ATM_RECONCILIATION.security.models.SecurityParameter;
import com.example.ATM_RECONCILIATION.security.repos.SecurityParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityParameterService {

    @Value("${sp.authentication.mode}")
    private String sysParamAuthMode;
    
    @Value("${spv.weblogic}")
    private String sysParamWeblogic;
    
    @Autowired
    private SecurityParameterRepository securityParameterRepository;

    public Optional<SecurityParameter> findByParameterName(String parameterName) {
        return securityParameterRepository.findByParameterName(parameterName);
    }

    public String getAuthenticationMode() {
        return findByParameterName(sysParamAuthMode)
                .map(SecurityParameter::getParameterValue)
                .orElse(sysParamWeblogic); // Default to WebLogic if not set
    }
    

    @Transactional
    public SecurityParameter updateParameterValue(String parameterName, String parameterValue) {
        Optional<SecurityParameter> parameterOpt = securityParameterRepository.findByParameterName(parameterName);
        if (parameterOpt.isPresent()) {
            SecurityParameter parameter = parameterOpt.get();
            parameter.setParameterValue(parameterValue);
            return securityParameterRepository.save(parameter);
        } else {
            throw new RuntimeException("Parameter not found");
        }
    }
    
}