package com.example.ATM_RECONCILIATION.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserSearchRoleDto implements Serializable {
    Integer userId;
    String username;
    String displayName;
}
