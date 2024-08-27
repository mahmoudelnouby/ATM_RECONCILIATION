package com.example.ATM_RECONCILIATION.security.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SESSION_USER", schema = "bpm_adminstration")
public class UserSession {
    @Id
    @Column(name = "SESSION_ID")
    private String sessionId;
    @Column(name = "USER_NAME")
    private String userName;
}
