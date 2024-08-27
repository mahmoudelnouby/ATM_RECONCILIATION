package com.example.ATM_RECONCILIATION.Entities.Ids;

import com.example.ATM_RECONCILIATION.security.models.Role;
import com.example.ATM_RECONCILIATION.security.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class UserRoleId implements Serializable {
    //@JsonProperty("roleId")
    @JoinColumn(name = "ROLE_ID",referencedColumnName = "ROLE_ID")
    @ManyToOne( targetEntity = Role.class,fetch = FetchType.EAGER)
    Role roleId;

    // @JsonProperty("userId")
    @JoinColumn(name = "USER_ID",referencedColumnName = "USER_ID")
    @JsonIgnore
    @ManyToOne( targetEntity = User.class,fetch = FetchType.EAGER)
    User userId;

}
