package com.example.ATM_RECONCILIATION.security.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "SECURITY_PARAMETERS", schema = "bpm_adminstration")
public class SecurityParameter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parameterId;

    @Column(name = "parameter_name", unique = true, nullable = false)
    private String parameterName;

    @Column(name = "parameter_value", nullable = false)
    private String parameterValue;

    @Column(name = "parameter_description")
    private String parameterDescription;

}
