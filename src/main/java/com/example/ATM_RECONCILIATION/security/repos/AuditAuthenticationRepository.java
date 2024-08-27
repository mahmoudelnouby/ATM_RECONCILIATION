package com.example.ATM_RECONCILIATION.security.repos;

import com.example.ATM_RECONCILIATION.security.models.AuditSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditAuthenticationRepository extends JpaRepository<AuditSession,Integer> {
}
