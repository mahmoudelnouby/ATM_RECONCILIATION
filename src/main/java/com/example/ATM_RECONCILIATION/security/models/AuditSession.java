package com.example.ATM_RECONCILIATION.security.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "AUDIT_SESSION")
public class AuditSession {
    @Id
    @Column(name = "ACTION_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="AUDIT_SESSION_SEQ")
    @SequenceGenerator(name = "AUDIT_SESSION_SEQ", sequenceName="AUDIT_SESSION_SEQ", allocationSize = 1)
    private Integer actionId;
    @Column(name = "ACTION_NAME")
    private String actionName;
    @Column(name = "ACTION_DATE")
    private Date actionDate;
    @Column(name = "USER_NAME")
    private String username;
    @Column(name = "SESSION_ID")
    private String sessionId;
    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    public AuditSession(String actionName, Date actionDate, String username, String sessionId, String ipAddress) {
        this.actionName = actionName;
        this.actionDate = actionDate;
        this.username = username;
        this.sessionId = sessionId;
        this.ipAddress = ipAddress;
    }
}
