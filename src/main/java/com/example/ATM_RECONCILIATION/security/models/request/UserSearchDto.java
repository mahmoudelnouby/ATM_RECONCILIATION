package com.example.ATM_RECONCILIATION.security.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.ATM_RECONCILIATION.security.models.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserSearchDto implements Serializable {
    Integer userId;
    String username;
    String emailAddress;
    String displayName;
    Integer active;
    Integer locked;
    String searchRole;

}