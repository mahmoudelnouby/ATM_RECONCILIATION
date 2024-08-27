package com.example.ATM_RECONCILIATION.specification;

import com.example.ATM_RECONCILIATION.payload.request.AuditSearch;
import com.example.ATM_RECONCILIATION.security.models.Audit;
import org.springframework.data.jpa.domain.Specification;


import jakarta.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditSpec {
    public static Specification<Audit> withAllConditions(AuditSearch audit) {
        return (Root<Audit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            Predicate predicate  = criteriaBuilder.conjunction();

            if(audit.getActionId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("actionId"), audit.getActionId()));
            }
            if(audit.getActionName() != null && !audit.getActionName().isEmpty()){
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.get("actionName"), "%" + audit.getActionName() + "%"));
            }
            if(audit.getActionDate() != null) {
                try {
                    Date startDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(
                            new SimpleDateFormat("dd/MM/yyyy").format(audit.getActionDate()) + " 00:00:00");
                    Date endDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(
                            new SimpleDateFormat("dd/MM/yyyy").format(audit.getActionDate()) + " 23:59:59");

                    Predicate dateWithinRange = criteriaBuilder.between(root.get("actionDate"), startDate, endDate);
                    predicate = criteriaBuilder.and(predicate, dateWithinRange);
                } catch (ParseException e) {
                    throw new RuntimeException("error in search");
                }


            }
            if(audit.getUsername() != null && !audit.getUsername().isEmpty()){
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.get("username"), "%" + audit.getUsername() + "%"));
            }
            if(audit.getOtherDetails() != null && !audit.getOtherDetails().isEmpty()){
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.get("otherDetails"), "%" + audit.getOtherDetails() + "%"));

            }

            query.orderBy(criteriaBuilder.desc(root.get("actionDate")));
            return predicate;
        };
    }
}
