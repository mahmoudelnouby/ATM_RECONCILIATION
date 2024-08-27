package com.example.ATM_RECONCILIATION.security.repos;

import com.example.ATM_RECONCILIATION.security.models.SecurityParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface SecurityParameterRepository extends JpaRepository<SecurityParameter, Long> {
    Optional<SecurityParameter> findByParameterName(String parameterName);
    
}