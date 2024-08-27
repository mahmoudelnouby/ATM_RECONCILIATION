package com.example.ATM_RECONCILIATION.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UsersRoleResponse implements Serializable {
    Integer userId;
    String username;
}
