package com.example.ATM_RECONCILIATION.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "SYS_PARAMETERS_CONFIGURATION")
public class Sys_parameters_conf {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "JWT_SECRET")
  private String jwtSecret;

  @Column(name = "ENC_KEY_1")
  private String encKey1;

  @Column(name = "ENC_KEY_2")
  private String encKey2;

  @Column(name = "ALGO_NAME")
  private String algoName;

  @Column(name = "ALGO_TYPE")
  private String algoType;

}
