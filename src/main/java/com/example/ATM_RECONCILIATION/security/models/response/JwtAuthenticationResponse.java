package com.example.ATM_RECONCILIATION.security.models.response;

import com.example.ATM_RECONCILIATION.security.models.UserPermission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String token;
    private String displayname;
    private Set<UserPermission> userPermissionSet;

}