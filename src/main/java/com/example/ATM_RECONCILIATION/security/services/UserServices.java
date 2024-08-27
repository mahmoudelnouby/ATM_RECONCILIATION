package com.example.ATM_RECONCILIATION.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServices {
    UserDetailsService userDetailsService();
}