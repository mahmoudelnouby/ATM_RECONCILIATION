package com.example.ATM_RECONCILIATION.security.services;

import com.example.ATM_RECONCILIATION.security.enums.AuditType;
import com.example.ATM_RECONCILIATION.security.models.AuditSession;
import com.example.ATM_RECONCILIATION.security.repos.AuditAuthenticationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuditAuthenticationService {

  @Value("${module.name}")
  private String moduleName;

  private AuditAuthenticationRepository auditRepo;

  public AuditAuthenticationService(AuditAuthenticationRepository auditRepo) {
    this.auditRepo = auditRepo;
  }

  public void saveUserAction(String username, AuditType action, String id, String ip) {

    try {
      String auditMsg = String.format("%s %s %s", username, action.label, moduleName);
      AuditSession auditSession = new AuditSession(auditMsg, new Date(), username, id, ip);

      auditRepo.save(auditSession);
    } catch (Exception e) {
      
      e.printStackTrace();
    }
  }

}