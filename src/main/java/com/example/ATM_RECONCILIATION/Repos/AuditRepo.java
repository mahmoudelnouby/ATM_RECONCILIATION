package com.example.ATM_RECONCILIATION.Repos;

import com.example.ATM_RECONCILIATION.security.models.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AuditRepo extends JpaRepository<Audit, Integer>, JpaSpecificationExecutor<Audit> {
}
