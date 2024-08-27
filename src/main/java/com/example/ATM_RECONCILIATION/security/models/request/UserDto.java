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
public class UserDto implements Serializable {
    Integer userId;
    String username;
    String emailAddress;
    String displayName;
    Integer Active;

    public UserDto(Integer userId, String username, String emailAddress, String displayName) {
        this.userId = userId;
        this.username = username;
        this.emailAddress = emailAddress;
        this.displayName = displayName;
    }
}