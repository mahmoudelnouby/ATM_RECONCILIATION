package com.example.ATM_RECONCILIATION.Entities;

import com.example.ATM_RECONCILIATION.Entities.Ids.UserRoleId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.persistence.*;

@Table(name = "SC_USERROLE",schema = "bpm_adminstration")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRole {
    @EmbeddedId
    private UserRoleId userRoleId;
    @Column(name = "LAST_MODIFIED_BY")
    @JsonIgnore
    private String lastModifiedBy;


}
