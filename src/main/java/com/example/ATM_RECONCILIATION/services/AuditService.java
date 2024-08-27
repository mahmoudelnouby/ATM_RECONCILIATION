package com.example.ATM_RECONCILIATION.services;

import com.example.ATM_RECONCILIATION.Repos.AuditRepo;
import com.example.ATM_RECONCILIATION.payload.request.AuditSearch;
import com.example.ATM_RECONCILIATION.security.models.Audit;
import com.example.ATM_RECONCILIATION.specification.AuditSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.HashMap;


@Service
public class AuditService {

    AuditRepo auditRepo;
    AuditService(AuditRepo auditRepo){
        this.auditRepo = auditRepo;
    }

    public HashMap<String, Object> SearchAudit(AuditSearch search, int pageNumber, int pageSize){
        Specification<Audit> spec =  AuditSpec.withAllConditions(search);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
         Page<Audit> audits = auditRepo.findAll(spec,pageable);
         int totalPages =  audits.getTotalPages();
         long totalElements = audits.getTotalElements();
         HashMap<String,Object> result = new HashMap<>();
         result.put("content",audits.getContent());
         result.put("totalPages",totalPages);
         result.put("totalElements",totalElements);
         if (audits.isEmpty())
            throw new RuntimeException("no data found");
        return result;
    }

}
