package com.example.ATM_RECONCILIATION.security.repos;

import com.example.ATM_RECONCILIATION.security.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionsRepository extends JpaRepository<UserSession,String> {
}
