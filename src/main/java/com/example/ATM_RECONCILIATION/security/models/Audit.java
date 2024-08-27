package com.example.ATM_RECONCILIATION.security.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "SC_AUDIT",schema = "bpm_adminstration")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Audit implements Serializable {
    @Id
    @Column(name = "ACTION_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "bpm_adminstration.SC_A_SEQ")
    @SequenceGenerator(name = "bpm_adminstration.SC_A_SEQ",sequenceName = "bpm_adminstration.SC_A_SEQ",allocationSize = 1)
    Integer actionId;
    @Column(name = "ACTION_NAME")
    String actionName;
    @Column(name = "ACTION_DATE")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss",timezone = "Africa/Cairo")
    Date actionDate;
    @Column(name = "USER_NAME")
    String username;
    @Column(name = "OTHER_DETAIL")
    String otherDetails;

    public Audit(String actionName, String username) {
        this.actionName = actionName;
        this.username = username;
        this.actionDate = new Date();
    }
}
