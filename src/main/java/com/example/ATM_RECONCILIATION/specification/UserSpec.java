package com.example.ATM_RECONCILIATION.specification;



import com.example.ATM_RECONCILIATION.security.models.User;
import com.example.ATM_RECONCILIATION.security.models.request.UserSearchDto;
import org.springframework.data.jpa.domain.Specification;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserSpec {

    public static Specification<User> withAllConditions(UserSearchDto user) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(user.getUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("userId"),user.getUserId()));
            }
            if(user.getUsername() != null && !user.getUsername().isEmpty()){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("username")),
                        "%" + user.getUsername().toLowerCase() + "%"));
            }
            if(user.getDisplayName() != null && !user.getDisplayName().isEmpty()){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("displayName")),
                        "%" + user.getDisplayName().toLowerCase() + "%"));
            }
            if (user.getEmailAddress() != null && !user.getEmailAddress().isEmpty()){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("emailAddress")),
                        "%" + user.getEmailAddress().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }



}
