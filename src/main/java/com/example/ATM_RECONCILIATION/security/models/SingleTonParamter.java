package com.example.ATM_RECONCILIATION.security.models;

import org.springframework.stereotype.Component;

@Component
public class SingleTonParamter {
  private static Sys_parameters_conf instance = null;

  private SingleTonParamter() {}

  public static Sys_parameters_conf getInstance() {
    if (SingleTonParamter.instance == null) {
      SingleTonParamter.instance = new Sys_parameters_conf();
    }
    return SingleTonParamter.instance;
  }

  public static void SetInstance(Sys_parameters_conf sys_parameters_conf) {
    SingleTonParamter.instance = sys_parameters_conf;
  }


}
